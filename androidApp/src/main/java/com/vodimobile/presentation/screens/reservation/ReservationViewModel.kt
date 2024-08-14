package com.vodimobile.presentation.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val dataStoreStorage: UserDataStoreStorage,
    private val crmStorage: CrmStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {

    val reservationState = MutableStateFlow(ReservationState())
    val reservationEffect = MutableSharedFlow<ReservationEffect>()

    fun onIntent(intent: ReservationIntent) {
        when (intent) {

            ReservationIntent.GetAllCars -> {
                viewModelScope.launch {
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect { user ->
                        val userFromRemote =
                            supabaseStorage.getUser(password = user.password, phone = user.phone)
                        val crmEither = crmStorage.getCarList(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken
                        )
                        when (crmEither) {
                            is CrmEither.CrmData -> {

                                val car = crmEither.data.find { it.carId == reservationState.value.carId } ?: Car.empty()

                                reservationState.update {
                                    it.copy(selectedCar = car)
                                }
                            }

                            is CrmEither.CrmError -> {}

                            CrmEither.CrmLoading -> {}
                        }
                    }
                }
            }

            ReservationIntent.GetAllPLaces -> {
                viewModelScope.launch {
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect { user ->
                        val userFromRemote =
                            supabaseStorage.getUser(password = user.password, phone = user.phone)
                        val crmEither = crmStorage.getPlaces(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken
                        )
                        when (crmEither) {
                            is CrmEither.CrmData -> {
                                reservationState.update {
                                    it.copy(placeList = crmEither.data)
                                }
                            }

                            is CrmEither.CrmError -> {}

                            CrmEither.CrmLoading -> {}
                        }
                    }
                }
            }

            ReservationIntent.ReturnBack -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ReturnBack)
                }
            }

            is ReservationIntent.PlaceChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            placeId = intent.value.first,
                            place = intent.value.second
                        )
                    }
                }
            }

            is ReservationIntent.CommentsChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            comments = intent.value
                        )
                    }
                }
            }

            is ReservationIntent.ShowTimePicker -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ShowTimePicker(intent.type))
                }
            }

            ReservationIntent.ShowLoadingDialog -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ShowLoadingDialog)
                }
            }

            ReservationIntent.DismissLoadingDialog -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.DismissLoadingDialog)
                }
            }

            ReservationIntent.ShowDatePicker -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ShowDatePicker)
                }
            }

            ReservationIntent.GetBidCost -> {
//                viewModelScope.launch {
//                    val userFLow = dataStoreStorage.getUser()
//                    userFLow.collect { user ->
//                        val userFromRemote =
//                            supabaseStorage.getUser(password = user.password, phone = user.phone)
//                        val crmEither = crmStorage.getBidCost(
//                            accessToken = userFromRemote.accessToken,
//                            refreshToken = userFromRemote.refreshToken,
//                            bidCostParams = BidCostParams(
//                                car_id = reservationState.value.carId,
//                                begin = "${reservationState.value.date[0]} ${reservationState.value.startTime})",
//                                end = "${reservationState.value.date[1]} ${reservationState.value.endTime})",
//                                begin_place_id = reservationState.value.placeId,
//                                end_place_id = reservationState.value.placeId,
//                                services = null
//                            )
//                        )
//                        when (crmEither) {
//                            is CrmEither.CrmData -> {
//                                reservationState.update {
//                                    it.copy(bidCost = crmEither.data.cost.toString())
//                                }
//                            }
//
//                            is CrmEither.CrmError -> {}
//
//                            CrmEither.CrmLoading -> {}
//                        }
//                    }
//                }
            }

            ReservationIntent.PutBid -> {
                viewModelScope.launch {
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect {}
                    reservationEffect.emit(ReservationEffect.PutBid)
                }
            }
        }
    }
}

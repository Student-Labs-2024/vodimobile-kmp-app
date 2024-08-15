package com.vodimobile.presentation.screens.reservation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import com.vodimobile.presentation.screens.reservation.utils.getEndDateTime
import com.vodimobile.presentation.screens.reservation.utils.getStartDateTime
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

    @RequiresApi(Build.VERSION_CODES.O)
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

                                val car =
                                    crmEither.data.find { it.carId == reservationState.value.carId }
                                        ?: Car.empty()

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
                            place = intent.value.second,
                            errorPlace = intent.value.second.isEmpty()
                        )
                    }
                }
            }

            is ReservationIntent.StartTimeChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            startTime = intent.value,
                            errorStartTime = intent.value.isEmpty()
                        )
                    }
                }
            }

            is ReservationIntent.EndTimeChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            endTime = intent.value,
                            errorEndTime = intent.value.isEmpty()
                        )
                    }
                }
            }

            is ReservationIntent.DateChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            date = intent.value,
                            errorDate = intent.value.contentEquals(longArrayOf(0L, 0L))
                        )
                    }
                }
            }

            is ReservationIntent.CarIdChange -> {
                viewModelScope.launch {
                    reservationState.update {
                        it.copy(
                            carId = intent.value
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

            is ReservationIntent.GetCarFreeDate -> {
                viewModelScope.launch {
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect { user ->
                        val userFromRemote =
                            supabaseStorage.getUser(password = user.password, phone = user.phone)
                        val startFreeDate = getStartFreeDateTime()
                        val endFreeDate = getEndFreeDateTime(startFreeDate)
                        val freeDates = crmStorage.getCarFreeDateRange(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken,
                            carId = reservationState.value.carId,
                            begin = startFreeDate,
                            end = endFreeDate
                        )
                        reservationState.update {
                            it.copy(freeDates = freeDates)
                        }
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
                viewModelScope.launch {
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect { user ->
                        val userFromRemote =
                            supabaseStorage.getUser(
                                password = user.password,
                                phone = user.phone
                            )
                        val crmEither = crmStorage.getBidCost(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken,
                            bidCostParams = BidCostParams(
                                car_id = reservationState.value.carId,
                                begin = "${reservationState.value.date[0]} ${reservationState.value.startTime})",
                                end = "${reservationState.value.date[1]} ${reservationState.value.endTime})",
                                begin_place_id = reservationState.value.placeId,
                                end_place_id = reservationState.value.placeId,
                                services = null
                            )
                        )
                        when (crmEither) {
                            is CrmEither.CrmData -> {
                                reservationState.update {
                                    it.copy(bidCost = crmEither.data.cost.toString())
                                }
                            }

                            is CrmEither.CrmError -> {}

                            CrmEither.CrmLoading -> {}
                        }
                    }
                }
            }

            ReservationIntent.PutBid -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ShowLoadingDialog)
                    val userFLow = dataStoreStorage.getUser()
                    userFLow.collect { user ->
                        val userFromRemote =
                            supabaseStorage.getUser(
                                password = user.password,
                                phone = user.phone
                            )
                        val crmEither = crmStorage.createBid(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken,
                            bidCreateParams = BidCreateParams(
                                fio = userFromRemote.fullName,
                                phone = userFromRemote.phone,
                                car_id = reservationState.value.carId,
                                begin = "${reservationState.value.date[0]} ${reservationState.value.startTime})",
                                end = "${reservationState.value.date[1]} ${reservationState.value.endTime})",
                                begin_place_id = reservationState.value.placeId,
                                end_place_id = reservationState.value.placeId,
                                services = null,
                                prepayment = null,
                                files = null
                            )
                        )
                        when (crmEither) {
                            is CrmEither.CrmData -> {}

                            is CrmEither.CrmError -> {}

                            CrmEither.CrmLoading -> {}
                        }
                    }
                    reservationEffect.emit(ReservationEffect.DismissLoadingDialog)
                    reservationEffect.emit(ReservationEffect.PutBid)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getStartFreeDateTime(): Long {
    return getStartDateTime()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getEndFreeDateTime(start: Long): Long {
    return getEndDateTime(start)
}

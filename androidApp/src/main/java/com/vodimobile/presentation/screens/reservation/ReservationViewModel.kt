package com.vodimobile.presentation.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ReservationViewModel(
    private val dataStoreStorage: UserDataStoreStorage,
    private val crmStorage: CrmStorage
) : ViewModel() {

    val reservationState = MutableStateFlow(ReservationState())
    val reservationEffect = MutableSharedFlow<ReservationEffect>()

    fun onIntent(intent: ReservationIntent) {
        when (intent) {

            ReservationIntent.GetAllPLaces -> {
                val userFLow = dataStoreStorage.getUser()
                viewModelScope.launch {
                    userFLow.collect { user ->
                        val places = crmStorage.getPlaces(
                            accessToken = user.accessToken,
                            refreshToken = user.refreshToken
                        )
                        reservationState.update {
                            it.copy(places = places)
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

            ReservationIntent.ShowTimePicker -> {
                viewModelScope.launch {
                    reservationEffect.emit(ReservationEffect.ShowTimePicker)
                }
            }

            ReservationIntent.ShowLoadingDialog -> {
                viewModelScope.launch {
                    delay(1.seconds)
                    reservationEffect.emit(ReservationEffect.ShowLoadingDialog)
                }
            }

            ReservationIntent.DismissLoadingDialog -> {
                viewModelScope.launch {
                    delay(1.seconds)
                    reservationEffect.emit(ReservationEffect.DismissLoadingDialog)
                }
            }

            ReservationIntent.GetBidCost -> {
                val userFLow = dataStoreStorage.getUser()
                viewModelScope.launch {
                    userFLow.collect { user ->
                        val bidCost = crmStorage.getBidCost(
                            accessToken = user.accessToken,
                            refreshToken = user.refreshToken,
                            bidCostParams = BidCostParams(
                                car_id = reservationState.value.car.carId,
                                begin = "${reservationState.value.date[0]} ${reservationState.value.time})",
                                end = "${reservationState.value.date[1]} ${reservationState.value.time})",
                                begin_place_id = reservationState.value.placeId,
                                end_place_id = reservationState.value.placeId,
                                services = null
                            )
                        )
                        reservationState.update {
                            it.copy(bidCost = bidCost)
                        }
                    }
                }
            }

            ReservationIntent.PutBid -> {
                val userFLow = dataStoreStorage.getUser()
                viewModelScope.launch {
                    userFLow.collect {}
                    reservationEffect.emit(ReservationEffect.PutBid)
                }
            }
        }
    }
}

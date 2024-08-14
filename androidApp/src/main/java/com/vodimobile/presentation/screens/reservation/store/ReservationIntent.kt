package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.presentation.screens.reservation.utils.TimeType

sealed class ReservationIntent {
    data object GetAllPLaces: ReservationIntent()
    data object GetAllCars: ReservationIntent()
    data class PlaceChange(val value: Pair<Int, String>): ReservationIntent()
    data class CommentsChange(val value: String): ReservationIntent()
    data object PutBid: ReservationIntent()
    data object ReturnBack : ReservationIntent()
    data class ShowTimePicker(val type: TimeType): ReservationIntent()
    data object DismissLoadingDialog: ReservationIntent()
    data object ShowLoadingDialog: ReservationIntent()
    data object GetBidCost: ReservationIntent()
    data object ShowDatePicker: ReservationIntent()
}

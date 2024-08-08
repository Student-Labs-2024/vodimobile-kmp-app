package com.vodimobile.presentation.screens.reservation.store

sealed class ReservationIntent {
    data object GetAllPLaces: ReservationIntent()
    data class PlaceChange(val value: Pair<Int, String>): ReservationIntent()
    data class CommentsChange(val value: String): ReservationIntent()
    data object PutBid: ReservationIntent()
    data object ReturnBack : ReservationIntent()
    data object ShowTimePicker: ReservationIntent()
    data object DismissLoadingDialog: ReservationIntent()
    data object ShowLoadingDialog: ReservationIntent()
    data object GetBidCost: ReservationIntent()
}

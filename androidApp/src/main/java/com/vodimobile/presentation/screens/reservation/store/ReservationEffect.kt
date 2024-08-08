package com.vodimobile.presentation.screens.reservation.store

sealed class ReservationEffect {
    data object PutBid : ReservationEffect()
    data object ReturnBack : ReservationEffect()
    data object ShowTimePicker: ReservationEffect()
    data object DismissLoadingDialog: ReservationEffect()
    data object ShowLoadingDialog: ReservationEffect()
}

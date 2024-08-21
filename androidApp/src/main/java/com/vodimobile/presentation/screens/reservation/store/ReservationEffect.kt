package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.presentation.screens.reservation.utils.TimeType

sealed class ReservationEffect {
    data object PutBid : ReservationEffect()
    data object ReturnBack : ReservationEffect()
    data class ShowTimePicker(val type: TimeType): ReservationEffect()
    data object DismissLoadingDialog: ReservationEffect()
    data object ShowLoadingDialog: ReservationEffect()
    data object ShowDatePicker: ReservationEffect()
    data object EmitGeneralStateChange : ReservationEffect()
}

package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.presentation.screens.reservation.utils.TimeType

sealed class ReservationIntent {
    data object GetAllPLaces : ReservationIntent()
    data object GetAllCars : ReservationIntent()
    data object GetCarFreeDate : ReservationIntent()
    data class PlaceChange(val value: Pair<Int, String>) : ReservationIntent()
    data class CommentsChange(val value: String) : ReservationIntent()
    data class DateChange(val value: LongArray) : ReservationIntent() {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DateChange

            return value.contentEquals(other.value)
        }

        override fun hashCode(): Int {
            return value.contentHashCode()
        }
    }
    data class CarIdChange(val value: Int): ReservationIntent()
    data class StartTimeChange(val value: String) : ReservationIntent()
    data class EndTimeChange(val value: String) : ReservationIntent()
    data object PutBid : ReservationIntent()
    data object ReturnBack : ReservationIntent()
    data class ShowTimePicker(val type: TimeType) : ReservationIntent()
    data object DismissLoadingDialog : ReservationIntent()
    data object ShowLoadingDialog : ReservationIntent()
    data object GetBidCost : ReservationIntent()
    data object ShowDatePicker : ReservationIntent()
}

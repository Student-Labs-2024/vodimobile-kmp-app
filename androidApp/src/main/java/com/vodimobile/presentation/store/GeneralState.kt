package com.vodimobile.presentation.store

import com.vodimobile.domain.model.order.DateRange

data class GeneralState(
    val selectedDate: LongArray,
    val availableDates: List<DateRange> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeneralState

        if (!selectedDate.contentEquals(other.selectedDate)) return false
        if (availableDates != other.availableDates) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedDate.contentHashCode()
        result = 31 * result + availableDates.hashCode()
        return result
    }
}

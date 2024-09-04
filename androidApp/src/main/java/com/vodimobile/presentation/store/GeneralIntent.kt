package com.vodimobile.presentation.store

import com.vodimobile.domain.model.order.DateRange

sealed class GeneralIntent {
    data class ChangeSelectedDate(val value: LongArray) : GeneralIntent()
    data class ChangeAvailablePeriods(val value: List<DateRange>) : GeneralIntent()
    data object NoAuth : GeneralIntent()
}

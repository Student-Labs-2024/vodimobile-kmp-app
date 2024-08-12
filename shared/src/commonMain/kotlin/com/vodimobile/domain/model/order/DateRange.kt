package com.vodimobile.domain.model.order

import kotlinx.datetime.LocalDate

data class DateRange(
    val startDate: LocalDate,
    val endDate: LocalDate
)
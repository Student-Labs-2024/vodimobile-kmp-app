package com.vodimobile.utils.date_formats

import com.vodimobile.domain.model.order.DateRange

fun List<DateRange>.toUnavailableDates(): List<DateRange> {
    val list = this
    val unavailableDates = mutableListOf<DateRange>()
    list.forEachIndexed { index, dateRange ->
        if (index == 0) {
            unavailableDates.add(
                DateRange(
                    startDate = dateRange.endDate,
                    endDate = list[index + 1].startDate
                )
            )
        }
        if (index != 0 && index != list.lastIndex - 1) {
            unavailableDates.add(
                DateRange(
                    startDate = dateRange.endDate,
                    endDate = dateRange.startDate
                )
            )
        }
        if (index == list.lastIndex - 1) {
            unavailableDates.add(
                DateRange(
                    startDate = dateRange.endDate,
                    endDate = list[index + 1].startDate
                )
            )
        }
    }
    unavailableDates.removeAt(list.lastIndex)

    return unavailableDates.toList()
}
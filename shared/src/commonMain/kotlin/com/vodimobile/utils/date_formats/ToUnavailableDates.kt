package com.vodimobile.utils.date_formats

import com.vodimobile.domain.model.order.DateRange
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun List<DateRange>.toUnavailableDates(): List<DateRange> {
    val list = this
    val unavailableDates = mutableListOf<DateRange>()
    val timeZone = TimeZone.currentSystemDefault()
    val fromEpochMillisecondsStart: Instant = Instant.fromEpochMilliseconds(this[0].startDate)
    val localDateTimeStart: LocalDateTime =
        fromEpochMillisecondsStart.toLocalDateTime(timeZone)
    val fromEpochMillisecondsEnd: Instant =
        Instant.fromEpochMilliseconds(this.lastOrNull()!!.endDate)
    val localDateTimeEnd: LocalDateTime =
        fromEpochMillisecondsEnd.toLocalDateTime(timeZone)
    val before = LocalDateTime(
        LocalDate(
            year = (localDateTimeStart.year - 1),
            monthNumber = localDateTimeStart.monthNumber,
            dayOfMonth = localDateTimeStart.dayOfMonth
        ),
        localDateTimeStart.time
    ).toInstant(timeZone).toEpochMilliseconds()
    val after = LocalDateTime(
        LocalDate(
            year = (localDateTimeEnd.year + 1),
            monthNumber = localDateTimeEnd.monthNumber,
            dayOfMonth = localDateTimeEnd.dayOfMonth
        ),
        localDateTimeEnd.time
    ).toInstant(timeZone).toEpochMilliseconds()
    unavailableDates.add(
        DateRange(
            startDate = before,
            endDate = reduceDay(date = list[0].startDate)
        )
    )
        list.forEachIndexed { index, dateRange ->
            if (index == 0 && index != list.lastIndex) {
                unavailableDates.add(
                    DateRange(
                        startDate = dateRange.endDate,
                        endDate = increaseDay(date = list[index + 1].startDate)
                    )
                )
            }
            if (index != 0 && index != list.lastIndex) {
                unavailableDates.add(
                    DateRange(
                        startDate = increaseDay(date = dateRange.endDate),
                        endDate = reduceDay(date = dateRange.startDate)
                    )
                )
            }
            if (index == list.lastIndex) {
                unavailableDates.add(
                    DateRange(
                        startDate = increaseDay(date = dateRange.endDate),
                        endDate = after
                    )
                )
            }
        }
    return unavailableDates.toList()
}

private fun reduceDay(timeZone: TimeZone = TimeZone.currentSystemDefault(), date: Long): Long {
    val tempInstant = Instant.fromEpochMilliseconds(date)
    val tempLDT = tempInstant.toLocalDateTime(timeZone)
    val tempLD = LocalDate(
        year = tempLDT.year,
        monthNumber = tempLDT.monthNumber,
        dayOfMonth = if (tempLDT.dayOfMonth == 1) tempLDT.dayOfMonth else (tempLDT.dayOfMonth - 1)
    )
    val tempLT = LocalTime(
        hour = tempLDT.hour,
        minute = tempLDT.minute
    )
    return LocalDateTime(
        tempLD,
        tempLT
    ).toInstant(timeZone).toEpochMilliseconds()
}

private fun increaseDay(timeZone: TimeZone = TimeZone.currentSystemDefault(), date: Long): Long {
    val tempInstant = Instant.fromEpochMilliseconds(date)
    val tempLDT = tempInstant.toLocalDateTime(timeZone)
    val tempLD = LocalDate(
        year = tempLDT.year,
        monthNumber = tempLDT.monthNumber,
        dayOfMonth = if (tempLDT.dayOfMonth == 28 || tempLDT.dayOfMonth == 31 || tempLDT.dayOfMonth == 30) tempLDT.dayOfMonth else (tempLDT.dayOfMonth + 1)
    )
    val tempLT = LocalTime(
        hour = tempLDT.hour,
        minute = tempLDT.minute
    )
    return LocalDateTime(
        tempLD,
        tempLT
    ).toInstant(timeZone).toEpochMilliseconds()
}
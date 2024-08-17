package com.vodimobile.utils.date_formats

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant


fun String.parseToLong(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return LocalDateTime.parse(input = this)
        .toInstant(timeZone = timeZone).toEpochMilliseconds()
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun String.parseDateToLong(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val formatPattern = "dd.MM.yyyy"
    val localDate = LocalDate.Format {
        byUnicodePattern(formatPattern)
    }.parse(this)

    val localDateTime = LocalDateTime(date = localDate, time = LocalTime(0, 0))

    return localDateTime
        .toInstant(timeZone = timeZone).toEpochMilliseconds()
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun String.parseTimeToLong(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val formatPattern = "HH:mm"

    val localTime = LocalTime.Format {
        byUnicodePattern(formatPattern)
    }.parse(this)

    val localDateTime = LocalDateTime(date = LocalDate(2020, 1, 1), time = localTime)
    return localDateTime
        .toInstant(timeZone = timeZone).toEpochMilliseconds()
}

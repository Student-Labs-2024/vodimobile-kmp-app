package com.vodimobile.utils.date_formats

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.parseToCrmDate(): String {

    val formatPattern = "dd-MM-yyyy"
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(formatPattern)
    }

    val fromEpochMilliseconds: Instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime =
        fromEpochMilliseconds.toLocalDateTime(TimeZone.of("Russia/Omsk"))
    val format: String = localDateTime.format(dateTimeFormat)


    return format
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.parseToSupabaseDate(): String {

    val formatPattern = "yyyy-dd-MM"
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(formatPattern)
    }

    val fromEpochMilliseconds: Instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime =
        fromEpochMilliseconds.toLocalDateTime(TimeZone.of("Russia/Omsk"))
    val format: String = localDateTime.format(dateTimeFormat)


    return format
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.parseToCrmDateTime(): String {

    val formatPattern = "yyyy-MM-dd hh:mm"
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(formatPattern)
    }

    val fromEpochMilliseconds: Instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime =
        fromEpochMilliseconds.toLocalDateTime(TimeZone.of("Russia/Omsk"))
    val format: String = localDateTime.format(dateTimeFormat)


    return format
}

package com.vodimobile.utils.date_formats

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun String.parseToLong(timeZone: TimeZone = TimeZone.of("Russia/Omsk")): Long {
    return LocalDateTime.parse(this)
        .toInstant(timeZone = timeZone).toEpochMilliseconds()
}
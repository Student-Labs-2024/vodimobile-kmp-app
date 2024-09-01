package com.vodimobile.utils.date

import com.vodimobile.utils.date_formats.parseToCrmDate
import com.vodimobile.utils.date_formats.parseToSupabaseDate
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatsTest {
    //Hello

    @Test
    fun toCrmFormatTest() {
        val actual = 1724476754000
        val expected = "2024-08-24"
        assertEquals(expected = expected, actual = actual.parseToCrmDate())
    }

    @Test
    fun toSupabaseOrdersFormatTest() {
        val actual = 1724476754000
        val expected = "24.08.2024"
        assertEquals(expected = expected, actual = actual.parseToSupabaseDate())
    }
}
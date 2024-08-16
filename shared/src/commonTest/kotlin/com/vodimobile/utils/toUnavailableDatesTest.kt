package com.vodimobile.utils

import com.vodimobile.domain.model.order.DateRange
import com.vodimobile.utils.date_formats.toUnavailableDates
import kotlin.test.Test
import kotlin.test.assertEquals


class ToUnavailableDatesTest {

    @Test
    fun toUnavailableDatesTest() {
        val list = listOf(
            DateRange(startDate = 1723792708000, endDate = 1724051908000),
            DateRange(startDate = 1724224708000, endDate = 1724224708000),
            DateRange(startDate = 1724829508000, endDate = 1725088708000),
        )

        val expected = listOf(
            DateRange(startDate = 1724051908000, endDate = 1724224708000),
            DateRange(startDate = 1724224708000, endDate = 1724829508000),
        )

        val toUnavailableDates = list.toUnavailableDates()
        assertEquals(toUnavailableDates, expected)
    }
}
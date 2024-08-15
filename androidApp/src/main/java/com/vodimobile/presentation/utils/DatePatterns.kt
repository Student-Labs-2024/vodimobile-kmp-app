package com.vodimobile.presentation.utils

import android.annotation.SuppressLint
import com.vodimobile.domain.model.order.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DatePatterns {
    @SuppressLint("ConstantLocale")
    fun fullDate(date: Long): String {
        return if (date == 0L || date < 0L) "" else SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        ).format(Date(date))
    }

    @SuppressLint("ConstantLocale")
    fun fullDate(date: LongArray): String {
        return if (date[0] == 0L) "" else
            "${
                SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale.getDefault()
                ).format(Date(date[0]))
            } - ${
                SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale.getDefault()
                ).format(Date(date[1]))
            }"
    }

    @SuppressLint("ConstantLocale")
    fun fullDateToStringRU(date: LongArray): String {
        if (date[0] == 0L || date[0] < 0L) return ""

        val startDate = Date(date[0])
        val endDate = Date(date[1])

        val startDay = SimpleDateFormat("d", Locale("ru")).format(startDate)
        val startMonth = SimpleDateFormat("MMMM", Locale("ru")).format(startDate)
        val endDay = SimpleDateFormat("d", Locale("ru")).format(endDate)
        val endMonth = SimpleDateFormat("MMMM", Locale("ru")).format(endDate)
        val year = SimpleDateFormat("yyyy", Locale("ru")).format(startDate)

        return if (startMonth == endMonth) {
            "$startDay-${endDay} $startMonth $year"
        } else {
            "$startDay $startMonth - $endDay $endMonth $year"
        }
    }

    fun formatRentalPeriod(order: Order): String {
        val start = Date(order.rentalDatePeriod.startDate)
        val end = Date(order.rentalDatePeriod.endDate)

        val dayStart = SimpleDateFormat("d", Locale("ru")).format(start)

        val monthStart = SimpleDateFormat("dd MMMM", Locale("ru")).format(start)
        val monthEnd = SimpleDateFormat("dd MMMM", Locale("ru")).format(end)

        val yearStart = SimpleDateFormat("YYYY", Locale("ru")).format(start)
        val yearEnd = SimpleDateFormat("YYYY", Locale("ru")).format(end)


        return when {
            yearStart == yearEnd && monthStart == monthEnd -> {
                // Если годы и месяцы совпадают
                "$dayStart - ${monthEnd} $yearStart"
            }

            yearStart == yearEnd -> {
                // Если только годы совпадают
                "${monthStart}  - ${monthEnd} $yearEnd"
            }

            else -> {
                // Если годы не совпадают
                "${monthStart} $yearStart - ${monthEnd} $yearEnd"
            }
        }
    }
}

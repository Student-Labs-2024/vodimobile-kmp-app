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

    fun formatRentalPeriod(order: Order): String {
        val start = Date(order.rentalDatePeriod.startDate)
        val end = Date(order.rentalDatePeriod.endDate)

        val dayStart = SimpleDateFormat("d", Locale.getDefault()).format(start)

        val monthStart = SimpleDateFormat("dd MMMM", Locale.getDefault()).format(start)
        val monthEnd = SimpleDateFormat("dd MMMM", Locale.getDefault()).format(end)

        val yearStart = SimpleDateFormat("YYYY", Locale.getDefault()).format(start)
        val yearEnd = SimpleDateFormat("YYYY", Locale.getDefault()).format(end)


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

    fun formatRentalTime(order: Order): String {
        val start = Date(order.rentalDatePeriod.startDate)
        val end = Date(order.rentalDatePeriod.endDate)

        val minuteStart = SimpleDateFormat("mm", Locale.getDefault()).format(start)
        val minuteEnd = SimpleDateFormat("mm", Locale.getDefault()).format(end)

        val hourStart = SimpleDateFormat("hh", Locale.getDefault()).format(start)
        val hourEnd = SimpleDateFormat("hh", Locale.getDefault()).format(end)

        val hourMinuteStart = SimpleDateFormat("hh:mm", Locale.getDefault()).format(start)
        val hourMinuteEnd = SimpleDateFormat("hh:mm", Locale.getDefault()).format(end)

        return when {
            hourStart == hourEnd -> {
                "$hourStart:$minuteStart-$minuteEnd"
            }

            else -> {
                "$hourMinuteStart-$hourMinuteEnd"
            }
        }
    }
}

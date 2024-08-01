package com.vodimobile.presentation.utils

import android.annotation.SuppressLint
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
}
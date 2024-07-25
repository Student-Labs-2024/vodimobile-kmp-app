package com.vodimobile.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DatePatterns {
    @SuppressLint("ConstantLocale")
    fun fullDate(date: Long) =
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(date))
}
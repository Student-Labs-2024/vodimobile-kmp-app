package com.vodimobile.presentation.utils.date_formats

import java.util.Calendar

fun increaseFreeYear(): Long {
    val current = System.currentTimeMillis()
    val cal = Calendar.getInstance()
    cal.timeInMillis = current
    cal.add(Calendar.MONTH, 6)

    return cal.timeInMillis
}

fun reduceFreeYear(): Long {
    val current = System.currentTimeMillis()
    val cal = Calendar.getInstance()
    cal.timeInMillis = current
    return cal.timeInMillis
}
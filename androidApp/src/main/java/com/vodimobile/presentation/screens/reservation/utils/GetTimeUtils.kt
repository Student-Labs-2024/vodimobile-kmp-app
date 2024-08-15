package com.vodimobile.presentation.screens.reservation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun getStartDateTime(): Long {
    val now = LocalDateTime.now()
    return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getEndDateTime(start: Long): Long {
    val startDate = Date(start)
    val startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    val endDateTime = startDateTime.plusYears(1)
    return endDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

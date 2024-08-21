package com.vodimobile.presentation.store

sealed class GeneralIntent {
    data class ChangeSelectedDate(val value: LongArray) : GeneralIntent()
}
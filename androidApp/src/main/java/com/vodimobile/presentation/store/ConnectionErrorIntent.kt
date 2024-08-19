package com.vodimobile.presentation.store

sealed class ConnectionErrorIntent {
    data class ClickRepeat(val value: String) : ConnectionErrorIntent()
}

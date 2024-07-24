package com.vodimobile.presentation.store

sealed class ConnectionErrorIntent {
    data object ClickRepeat : ConnectionErrorIntent()
}

package com.vodimobile.presentation.screens.network_error.store

sealed class ConnectionErrorIntent {
    data object ClickRepetir : ConnectionErrorIntent()
}
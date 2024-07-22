package com.vodimobile.presentation.screens.network_error.store

sealed class ConnectionErrorEffect {
    data object CloseRepetir : ConnectionErrorEffect()
}
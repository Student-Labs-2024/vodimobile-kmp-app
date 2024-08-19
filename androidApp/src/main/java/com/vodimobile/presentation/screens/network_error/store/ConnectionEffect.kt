package com.vodimobile.presentation.screens.network_error.store

sealed class ConnectionErrorEffect {
    data class ClickRepeat(val value: String) : ConnectionErrorEffect()
}

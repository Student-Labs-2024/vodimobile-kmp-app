package com.vodimobile.presentation.store

sealed class ConnectionErrorEffect {
    data class ClickRepeat(val value: String) : ConnectionErrorEffect()
}

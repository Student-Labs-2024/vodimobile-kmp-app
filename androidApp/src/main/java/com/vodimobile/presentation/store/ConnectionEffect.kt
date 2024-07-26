package com.vodimobile.presentation.store

sealed class ConnectionErrorEffect {
    data object ClickRepeat : ConnectionErrorEffect()
}

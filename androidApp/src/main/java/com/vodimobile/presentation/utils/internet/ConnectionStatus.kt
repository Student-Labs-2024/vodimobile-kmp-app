package com.vodimobile.presentation.utils.internet

sealed class ConnectionStatus {
    object  Available : ConnectionStatus()
    object Unavailable : ConnectionStatus()
}

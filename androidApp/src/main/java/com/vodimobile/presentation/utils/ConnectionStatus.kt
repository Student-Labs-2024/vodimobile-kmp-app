package com.vodimobile.presentation.utils

sealed class ConnectionStatus {
    object  Available : ConnectionStatus()
    object Unavailable : ConnectionStatus()
}
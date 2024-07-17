package com.vodimobile.presentation.screens.startscreen.store

sealed class StartScreenIntent {
    data object ClickRegistration : StartScreenIntent()
    data object ClickLogin : StartScreenIntent()
    data object CloseClick : StartScreenIntent()
}
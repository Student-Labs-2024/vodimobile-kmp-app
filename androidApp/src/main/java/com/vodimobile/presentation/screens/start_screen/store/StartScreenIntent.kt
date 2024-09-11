package com.vodimobile.presentation.screens.start_screen.store

sealed class StartScreenIntent {
    data object ClickRegistration : StartScreenIntent()
    data object ClickLogin : StartScreenIntent()
    data object CloseClick : StartScreenIntent()
}

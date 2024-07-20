package com.vodimobile.presentation.screens.start_screen.store

sealed class StartScreenEffect {
    data object CloseClick : StartScreenEffect()
    data object ClickRegistration : StartScreenEffect()
    data object ClickLogin : StartScreenEffect()
}
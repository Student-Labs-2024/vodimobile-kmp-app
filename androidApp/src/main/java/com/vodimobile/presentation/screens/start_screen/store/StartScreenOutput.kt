package com.vodimobile.presentation.screens.start_screen.store

sealed class StartScreenOutput {
    data object ClickRegistration : StartScreenOutput()
    data object ClickLogin : StartScreenOutput()
    data object CloseClick : StartScreenOutput()
}
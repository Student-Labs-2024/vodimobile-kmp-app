package com.vodimobile.presentation.screens.startscreen.store

sealed class StartScreenOutput {
    data object ClickRegistration : StartScreenOutput()
    data object ClickLogin : StartScreenOutput()
    data object CloseClick : StartScreenOutput()
}
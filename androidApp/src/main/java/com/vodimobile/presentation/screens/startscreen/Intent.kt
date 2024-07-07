package com.vodimobile.presentation.screens.startscreen

sealed class Intent {
    data object ClickRegistration : Intent()
    data object ClickLogin : Intent()
    data object CloseClick : Intent()
}
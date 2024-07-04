package com.vodimobile.presentation.startscreen

sealed class Intent {
    data object ClickRegistration : Intent()
    data object ClickLogin : Intent()
}
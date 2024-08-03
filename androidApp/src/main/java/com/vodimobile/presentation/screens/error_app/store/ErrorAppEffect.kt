package com.vodimobile.presentation.screens.error_app.store

sealed class ErrorAppEffect {
    data object BackClick : ErrorAppEffect()
    data object RepeatClick : ErrorAppEffect()
}
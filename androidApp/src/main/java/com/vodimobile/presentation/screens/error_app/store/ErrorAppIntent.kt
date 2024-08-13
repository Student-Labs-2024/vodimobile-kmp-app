package com.vodimobile.presentation.screens.error_app.store

sealed class ErrorAppIntent {
    data object BackClick : ErrorAppIntent()
    data object RepeatClick : ErrorAppIntent()
}
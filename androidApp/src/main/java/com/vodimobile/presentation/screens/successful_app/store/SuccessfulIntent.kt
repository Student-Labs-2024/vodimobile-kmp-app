package com.vodimobile.presentation.screens.successful_app.store

sealed class SuccessfulIntent {
    data object CloseClick : SuccessfulIntent()
    data object ClickOrders : SuccessfulIntent()
}
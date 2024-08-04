package com.vodimobile.presentation.screens.successful_app.store

sealed class SuccessfulEffect {
    data object CloseClick : SuccessfulEffect()
    data object ClickOrders : SuccessfulEffect()
}
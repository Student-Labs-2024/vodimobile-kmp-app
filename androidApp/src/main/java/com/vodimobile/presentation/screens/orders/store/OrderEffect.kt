package com.vodimobile.presentation.screens.orders.store


sealed class OrderEffect {
    data class InfoOrderClick(val orderId: Int) : OrderEffect()
    data object ShowProgressDialog : OrderEffect()
    data object DismissProgressDialog : OrderEffect()
}

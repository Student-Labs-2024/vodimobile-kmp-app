package com.vodimobile.presentation.screens.orders.store


sealed class OrderEffect {
    data object InfoOrderClick : OrderEffect()
    data object ShowProgressDialog : OrderEffect()
    data object DismissProgressDialog : OrderEffect()
}

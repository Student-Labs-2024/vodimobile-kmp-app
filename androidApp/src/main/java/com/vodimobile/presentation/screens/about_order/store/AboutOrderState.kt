package com.vodimobile.presentation.screens.about_order.store

import com.vodimobile.domain.model.order.Order

data class AboutOrderState(
    val order: Order = Order.empty()
)

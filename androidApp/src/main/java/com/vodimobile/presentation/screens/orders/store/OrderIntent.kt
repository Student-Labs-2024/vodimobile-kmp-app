package com.vodimobile.presentation.screens.orders.store

import com.vodimobile.domain.model.order.Order

sealed class OrderIntent {
    data class InfoOrderClick(val order: Order) : OrderIntent()
    data object ShowProgressDialog : OrderIntent()
    data object DismissProgressDialog : OrderIntent()
    data object InitCards : OrderIntent()
    data class SelectOrders(val index: Int) : OrderIntent()
    data object CanselAllCoroutines : OrderIntent()
}

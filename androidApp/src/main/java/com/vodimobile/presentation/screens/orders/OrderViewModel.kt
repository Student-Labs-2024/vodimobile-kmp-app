package com.vodimobile.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.orders.store.OrderEffect
import com.vodimobile.presentation.screens.orders.store.OrderIntent
import com.vodimobile.presentation.screens.orders.store.OrderState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrderViewModel() : ViewModel() {
    val orderState = MutableStateFlow(OrderState())

    val orderEffect = MutableSharedFlow<OrderEffect>()

    fun onIntent(intent: OrderIntent) {
        when (intent) {
            is OrderIntent.InfoOrderClick -> {
                viewModelScope.launch {
                    orderEffect.emit(OrderEffect.InfoOrderClick)
                }
            }
            OrderIntent.ShowProgressDialog -> {
                viewModelScope.launch {
                    orderEffect.emit(OrderEffect.ShowProgressDialog)
                }
            }
            OrderIntent.DismissProgressDialog -> {
                viewModelScope.launch {
                    orderEffect.emit(OrderEffect.DismissProgressDialog)
                }
            }
        }
    }
}

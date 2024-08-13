package com.vodimobile.presentation.screens.orders

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.orders.store.OrderState
import kotlinx.coroutines.flow.MutableStateFlow

class OrderViewModel() : ViewModel() {
    val orderState = MutableStateFlow(OrderState())
}
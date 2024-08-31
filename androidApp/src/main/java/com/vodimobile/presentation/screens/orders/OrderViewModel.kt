package com.vodimobile.presentation.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.order.CarStatus
import com.vodimobile.domain.model.order.Order
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.orders.store.OrderEffect
import com.vodimobile.presentation.screens.orders.store.OrderIntent
import com.vodimobile.presentation.screens.orders.store.OrderState
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    private val supabaseStorage: SupabaseStorage,
    private val userDataStoreStorage: UserDataStoreStorage
) : ViewModel() {
    val orderState = MutableStateFlow(OrderState())
    val orderEffect = MutableSharedFlow<OrderEffect>()
    private val supervisorCoroutineContext = viewModelScope.coroutineContext + SupervisorJob()
    private var activeOrdersJob: Job = Job()
    private var canselOrdersJob: Job = Job()

    fun onIntent(intent: OrderIntent) {
        when (intent) {
            is OrderIntent.InfoOrderClick -> {
                viewModelScope.launch {
                    orderEffect.emit(OrderEffect.InfoOrderClick(orderId = intent.order.orderId))
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

            OrderIntent.InitCards -> {
                activeOrdersJob = viewModelScope.launch(context = supervisorCoroutineContext) {
                    getOrders()
                }
            }

            is OrderIntent.SelectOrders -> {
                canselOrdersJob = viewModelScope.launch(context = supervisorCoroutineContext) {
                    getOrders(index = intent.index)
                }
            }

            OrderIntent.CanselAllCoroutines -> {
                supervisorCoroutineContext.cancelChildren()
            }
        }
    }

    private suspend fun getOrders(index: Int = 0) {

        if (index == 0) {
            canselOrdersJob.cancel()
        } else if (index == 1) {
            activeOrdersJob.cancel()
        }

        orderState.update {
            it.copy(
                isLoading = true
            )
        }

        val userFlow = userDataStoreStorage.getUser()

        userFlow.collect { value ->
            var user: User = value
            if (user == User.empty()) {
                orderState.update { it ->
                    it.copy(
                        orders = emptyList(),
                        isLoading = false
                    )
                }
            } else {
                user = supabaseStorage.getUser(password = user.password, phone = user.phone)

                val orders: List<Order> =
                    supabaseStorage.getOrders(
                        userId = user.id,
                        accessToken = user.accessToken,
                        refreshToken = user.refreshToken,
                        phone = user.phone
                    )

                orderState.update { it ->
                    it.copy(
                        orders = orders.filter { it.status.filterByCarStatus(key = index) },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun CarStatus.filterByCarStatus(key: Int): Boolean {
        return if (key == 0) {
            this == CarStatus.Approved || this == CarStatus.Processing || this == CarStatus.Reserve
        } else {
            this == CarStatus.Cancelled || this == CarStatus.Completed
        }
    }
}

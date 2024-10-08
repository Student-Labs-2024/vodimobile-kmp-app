package com.vodimobile.presentation.screens.about_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.order.Order
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.about_order.store.AboutOrderEffect
import com.vodimobile.presentation.screens.about_order.store.AboutOrderIntent
import com.vodimobile.presentation.screens.about_order.store.AboutOrderState
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AboutOrderViewModel(
    private val userDataStoreStorage: UserDataStoreStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {
    val aboutOrderState = MutableStateFlow(AboutOrderState())
    val aboutOrderEffect = MutableSharedFlow<AboutOrderEffect>()
    private val supervisorCoroutineContext = viewModelScope.coroutineContext + SupervisorJob()

    fun onAboutOrderIntent(intent: AboutOrderIntent) {
        when (intent) {
            AboutOrderIntent.BackClick -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.BackClick)
                }
            }

            AboutOrderIntent.CanselOrder -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.ShowDialog)
                }
            }

            AboutOrderIntent.ChangeOrderClick -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.ChangeOrderClick)
                }
            }

            is AboutOrderIntent.InitOrder -> {
                viewModelScope.launch(supervisorCoroutineContext) {
                    initOrder(orderId = intent.orderId)
                }
            }

            AboutOrderIntent.CanselCoroutines -> {
                supervisorCoroutineContext.cancelChildren()
            }
        }
    }

    private suspend inline fun initOrder(orderId: Int) {
        val userFlow = userDataStoreStorage.getUser()
        userFlow.collect { value ->
            val user: User =
                supabaseStorage.getUser(password = value.password, phone = value.phone)
            val order: Order = supabaseStorage
                .getOrders(
                    userId = user.id,
                    accessToken = user.accessToken,
                    refreshToken = user.refreshToken,
                    phone = user.phone
                ).single {
                    it.orderId == orderId
                }
            aboutOrderState.update {
                it.copy(
                    order = order,
                    isLoading = false
                )
            }
        }
    }
}

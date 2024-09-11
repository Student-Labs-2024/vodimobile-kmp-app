package com.vodimobile.presentation.screens.successful_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.successful_app.store.SuccessfulEffect
import com.vodimobile.presentation.screens.successful_app.store.SuccessfulIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SuccessfulAppViewModel : ViewModel() {
    val successfulEffect = MutableSharedFlow<SuccessfulEffect>()

    fun onIntent(intent: SuccessfulIntent) {
        when (intent) {
            SuccessfulIntent.ClickOrders -> {
                viewModelScope.launch {
                    successfulEffect.emit(SuccessfulEffect.ClickOrders)
                }
            }

            SuccessfulIntent.CloseClick -> {
                viewModelScope.launch {
                    successfulEffect.emit(SuccessfulEffect.CloseClick)
                }
            }
        }
    }
}

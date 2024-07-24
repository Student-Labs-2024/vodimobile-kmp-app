package com.vodimobile.presentation.screens.network_error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorEffect
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorIntent
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConnectionErrorViewModel : ViewModel() {
    val connectionErrorEffect = MutableSharedFlow<ConnectionErrorEffect>()

    fun onIntent(intent: ConnectionErrorIntent) {
        when (intent) {
            ConnectionErrorIntent.ClickRepetir -> {
                viewModelScope.launch {
                    connectionErrorEffect.emit(ConnectionErrorEffect.CloseRepetir)
                }
            }
        }
    }
}
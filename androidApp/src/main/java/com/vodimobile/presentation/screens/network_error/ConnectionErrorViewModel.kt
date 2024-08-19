package com.vodimobile.presentation.screens.network_error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.store.ConnectionErrorEffect
import com.vodimobile.presentation.store.ConnectionErrorIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ConnectionErrorViewModel : ViewModel() {
    val connectionErrorEffect = MutableSharedFlow<ConnectionErrorEffect>()

    fun onIntent(intent: ConnectionErrorIntent) {
        when (intent) {
            is ConnectionErrorIntent.ClickRepeat -> {
                viewModelScope.launch {
                    connectionErrorEffect.emit(ConnectionErrorEffect.ClickRepeat(value = intent.value))
                }
            }
        }
    }
}
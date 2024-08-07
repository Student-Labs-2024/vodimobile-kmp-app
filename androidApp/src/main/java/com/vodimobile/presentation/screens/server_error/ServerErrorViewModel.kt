package com.vodimobile.presentation.screens.server_error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.store.ConnectionErrorEffect
import com.vodimobile.presentation.store.ConnectionErrorIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ServerErrorViewModel : ViewModel() {

    val serverErrorEffect = MutableSharedFlow<ConnectionErrorEffect>()

    fun onIntent(intent: ConnectionErrorIntent) {
        when (intent) {
            is ConnectionErrorIntent.ClickRepeat -> {
                viewModelScope.launch {
                    serverErrorEffect.emit(ConnectionErrorEffect.ClickRepeat(value = intent.value))
                }
            }
        }
    }
}

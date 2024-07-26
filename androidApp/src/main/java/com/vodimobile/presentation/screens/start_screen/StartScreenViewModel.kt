package com.vodimobile.presentation.screens.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.start_screen.store.StartScreenEffect
import com.vodimobile.presentation.screens.start_screen.store.StartScreenIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class StartScreenViewModel : ViewModel() {

    val startScreenEffect = MutableSharedFlow<StartScreenEffect>()

    fun onIntent(startScreenIntent: StartScreenIntent) {
        when (startScreenIntent) {
            StartScreenIntent.ClickLogin -> {
                viewModelScope.launch {
                    startScreenEffect.emit(StartScreenEffect.ClickLogin)
                }
            }

            StartScreenIntent.ClickRegistration -> {
                viewModelScope.launch {
                    startScreenEffect.emit(StartScreenEffect.ClickRegistration)
                }
            }

            StartScreenIntent.CloseClick -> {
                viewModelScope.launch {
                    startScreenEffect.emit(StartScreenEffect.CloseClick)
                }
            }
        }
    }
}
package com.vodimobile.presentation.startscreen

import androidx.lifecycle.ViewModel

class StartScreenViewModel : ViewModel() {

    fun onIntent(intent: Intent) {
        when (intent) {
            Intent.ClickLogin -> {}
            Intent.ClickRegistration -> {}
            Intent.CloseClick -> {}
        }
    }
}
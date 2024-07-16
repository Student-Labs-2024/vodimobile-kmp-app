package com.vodimobile.presentation.screens.faq

import androidx.lifecycle.ViewModel

class FaqViewModel : ViewModel() {
    fun onIntent(intent: FaqIntent) {
        when (intent) {
            FaqIntent.BackClick -> {}
        }
    }
}
package com.vodimobile.presentation.screens.faq

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.faq.store.FaqIntent
import com.vodimobile.presentation.screens.faq.store.FaqOutput

class FaqViewModel(private val output: (FaqOutput) -> Unit) : ViewModel() {
    fun onIntent(intent: FaqIntent) {
        when (intent) {
            FaqIntent.BackClick -> {
                onOutput(FaqOutput.BackClick)
            }
        }
    }

    private fun onOutput(o: FaqOutput) {
        output(o)
    }
}
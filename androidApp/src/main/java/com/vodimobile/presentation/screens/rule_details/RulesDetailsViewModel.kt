package com.vodimobile.presentation.screens.rule_details

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.vodimobile.presentation.screens.rule_details.store.RulesDetailsIntent
import com.vodimobile.presentation.screens.rule_details.store.RulesDetailsOutput

class RulesDetailsViewModel(private val output: (RulesDetailsOutput) -> Unit) : ViewModel() {
    fun onIntent(intent: RulesDetailsIntent) {
        when (intent) {
            RulesDetailsIntent.ReturnBack -> {
                onOutput(RulesDetailsOutput.ReturnBack)
            }
        }
    }

    private fun onOutput(o: RulesDetailsOutput) {
        output(o)
    }
}

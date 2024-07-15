package com.vodimobile.presentation.screens.rules

import androidx.lifecycle.ViewModel

class RulesViewModel : ViewModel() {
    fun onIntent(intent: RulesIntent) {
        when (intent) {
            RulesIntent.BackClick -> {}
            is RulesIntent.RuleClick ->  {}
        }
    }



}
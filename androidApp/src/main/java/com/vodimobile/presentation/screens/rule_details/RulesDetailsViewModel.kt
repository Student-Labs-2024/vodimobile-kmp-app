package com.vodimobile.presentation.screens.rule_details

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class RulesDetailsViewModel(private val navController: NavHostController) : ViewModel() {
    fun onIntent(intent: RulesDetailsIntent) {
        when (intent) {
            RulesDetailsIntent.ReturnBack -> {
                navController.popBackStack()
            }
        }
    }
}

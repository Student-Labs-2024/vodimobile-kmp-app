package com.vodimobile.presentation.screens.rules

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.vodimobile.presentation.LeafScreen

class RuleViewModel(private val navController: NavHostController) : ViewModel() {
    fun onIntent(intent: RulesIntent) {
        when (intent) {
            RulesIntent.BackClick -> {
                navController.popBackStack()
            }
            is RulesIntent.RuleClick -> {
                navController.navigate("${LeafScreen.RULE_DETAILS_SCREEN}/${intent.ruleId}")
            }
        }
    }
}

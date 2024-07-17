package com.vodimobile.presentation.screens.rules.store

sealed class RulesOutput {
    data object BackClick : RulesOutput()
    data class RuleClick(val ruleId: Int) : RulesOutput()
}
package com.vodimobile.presentation.screens.rules.store

sealed class RulesIntent {
    data object BackClick : RulesIntent()
    data class RuleClick(val ruleId: Int) : RulesIntent()
}

package com.vodimobile.presentation.screens.rules.store

sealed class RulesEffect {
    data object BackClick : RulesEffect()
    data class RuleClick(val ruleId: Int) : RulesEffect()
}

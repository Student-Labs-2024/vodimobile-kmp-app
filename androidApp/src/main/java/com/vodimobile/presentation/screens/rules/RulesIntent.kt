package com.vodimobile.presentation.screens.rules

import com.vodimobile.domain.model.RulesAndCondition

sealed class RulesIntent {
    data object BackClick : RulesIntent()
    data class RuleClick(val rulesAndCondition: RulesAndCondition) : RulesIntent()

}
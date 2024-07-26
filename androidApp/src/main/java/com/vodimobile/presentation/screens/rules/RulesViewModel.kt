package com.vodimobile.presentation.screens.rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.repository.rules_and_condition.RulesAndConditionRepository
import com.vodimobile.presentation.screens.rules.store.RulesEffect
import com.vodimobile.presentation.screens.rules.store.RulesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RulesViewModel(
    rulesAndConditionRepository: RulesAndConditionRepository
) : ViewModel() {

    val rulesEffect = MutableSharedFlow<RulesEffect>()
    val rulesState =
        MutableStateFlow(RulesState(rulesList = rulesAndConditionRepository.getRulesAndCondition()))

    fun onIntent(intent: RulesIntent) {
        when (intent) {
            RulesIntent.BackClick -> {
                viewModelScope.launch {
                    rulesEffect.emit(RulesEffect.BackClick)
                }
            }

            is RulesIntent.RuleClick -> {
                viewModelScope.launch {
                    rulesEffect.emit(RulesEffect.RuleClick(ruleId = intent.ruleId))
                }
            }

            else -> {}
        }
    }
}

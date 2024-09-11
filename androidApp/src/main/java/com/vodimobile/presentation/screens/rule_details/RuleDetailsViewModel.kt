package com.vodimobile.presentation.screens.rule_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.domain.repository.rules_and_condition.RulesAndConditionRepository
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsEffect
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsIntent
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RuleDetailsViewModel(
    ruleDetailsRepository: RulesAndConditionRepository,
) : ViewModel() {

    val rulesDetailsEffect = MutableSharedFlow<RuleDetailsEffect>()
    val ruleDetailsState =
        MutableStateFlow(RuleDetailsState(rulesAndConditionModelList = ruleDetailsRepository.getRulesAndCondition()))

    fun onIntent(intent: RuleDetailsIntent) {
        when (intent) {
            RuleDetailsIntent.ReturnBack -> {
                viewModelScope.launch {
                    rulesDetailsEffect.emit(RuleDetailsEffect.BackClick)
                }
            }
        }
    }
}

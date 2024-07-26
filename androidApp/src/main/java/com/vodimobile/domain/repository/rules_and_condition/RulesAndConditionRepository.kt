package com.vodimobile.domain.repository.rules_and_condition

import com.vodimobile.domain.model.RulesAndConditionModel

interface RulesAndConditionRepository {
    fun getRulesAndCondition(): List<RulesAndConditionModel>
}
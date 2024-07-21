package com.vodimobile.data.repository.rules_and_condition

import android.content.Context
import com.vodimobile.android.R
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.domain.repository.rules_and_condition.RulesAndConditionRepository

class RulesAndConditionRepositoryImpl(private val context: Context) : RulesAndConditionRepository {
    override fun getRulesAndCondition(): List<RulesAndConditionModel> {
        val rules = context.resources.getStringArray(R.array.rules)
        val titles = context.resources.getStringArray(R.array.title)
        val condition = context.resources.getStringArray(R.array.condition)


        if (rules.size != titles.size) {
            throw IllegalArgumentException("The number of questions and answers must be equal")
        }

        val rulesAndConditionList = mutableListOf<RulesAndConditionModel>()
        rules.forEachIndexed { index, s ->
            rulesAndConditionList.add(
                RulesAndConditionModel(
                    rule = titles[index],
                    title = rules[index],
                    condition = condition[index]
                )
            )
        }
        return rulesAndConditionList
    }
}
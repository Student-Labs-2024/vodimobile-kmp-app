package com.vodimobile.domain.model

import android.content.res.Resources
import com.vodimobile.android.R

data class RulesAndConditionModel(
    val rule: String,
    val title: String,
    val condition: String
) {
    companion object {
        fun getRulesAndConditionModelList(resources: Resources): List<RulesAndConditionModel> {
            val rules = resources.getStringArray(R.array.rules)
            val titles = resources.getStringArray(R.array.title)
            val condition = resources.getStringArray(R.array.condition)


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
}


package com.vodimobile.model

import com.vodimobile.android.R

data class RulesAndCondition (
    val title : Int,
    val subtitle : Int,
    val description : Int
)

val mockList = listOf(
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
    RulesAndCondition(title = R.string.str_rules1, subtitle = R.string.str_rules3, description = R.string.str_rules4),
)
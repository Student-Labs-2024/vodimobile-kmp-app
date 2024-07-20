package com.vodimobile.presentation.screens.rule_details.store

sealed class RuleDetailsIntent {
    data object ReturnBack : RuleDetailsIntent()
}

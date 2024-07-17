package com.vodimobile.presentation.screens.rule_details.store

sealed class RulesDetailsIntent {
    data object ReturnBack : RulesDetailsIntent()
}

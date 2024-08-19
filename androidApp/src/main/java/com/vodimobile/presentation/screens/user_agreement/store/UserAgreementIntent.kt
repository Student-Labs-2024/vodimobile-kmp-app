package com.vodimobile.presentation.screens.user_agreement.store

sealed class UserAgreementIntent {
    data object ReturnBack : UserAgreementIntent()
}

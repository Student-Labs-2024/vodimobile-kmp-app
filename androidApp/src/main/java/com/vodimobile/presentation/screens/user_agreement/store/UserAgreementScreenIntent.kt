package com.vodimobile.presentation.screens.user_agreement.store

sealed class UserAgreementScreenIntent {
    data object ReturnBack : UserAgreementScreenIntent()
}

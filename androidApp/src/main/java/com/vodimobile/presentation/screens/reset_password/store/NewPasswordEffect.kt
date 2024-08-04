package com.vodimobile.presentation.screens.reset_password.store

sealed class NewPasswordEffect {
    data object ReturnBack : NewPasswordEffect()
    data object SaveData: NewPasswordEffect()
    data object OpenUserAgreement : NewPasswordEffect()
}

package com.vodimobile.presentation.screens.reset_password.store

sealed class NewPasswordIntent {
    data class PasswordChange(val value: String) : NewPasswordIntent()
    data object ReturnBack : NewPasswordIntent()
    data object SaveData: NewPasswordIntent()
    data object OpenUserAgreement: NewPasswordIntent()
}

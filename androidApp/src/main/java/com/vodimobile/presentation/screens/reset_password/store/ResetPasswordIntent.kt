package com.vodimobile.presentation.screens.reset_password.store

sealed class ResetPasswordIntent {
    data class PhoneNumberChange(val value: String) : ResetPasswordIntent()
    data object SmsVerification : ResetPasswordIntent()
    data object ReturnBack : ResetPasswordIntent()
    data object AskPermission: ResetPasswordIntent()
}

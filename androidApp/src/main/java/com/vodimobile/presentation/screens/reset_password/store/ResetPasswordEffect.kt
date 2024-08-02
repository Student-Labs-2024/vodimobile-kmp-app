package com.vodimobile.presentation.screens.reset_password.store

sealed class ResetPasswordEffect {
    data object SmsVerification : ResetPasswordEffect()
    data object ReturnBack : ResetPasswordEffect()
    data object AskPermission: ResetPasswordEffect()
}

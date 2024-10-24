package com.vodimobile.presentation.screens.authorization.store

sealed class AuthorizationIntent {
    data object OpenUserAgreement : AuthorizationIntent()
    data object SmsVerification : AuthorizationIntent()
    data object ReturnBack : AuthorizationIntent()
    data class PhoneNumberChange(val value: String) : AuthorizationIntent()
    data class PasswordChange(val value: String): AuthorizationIntent()
    data object AskPermission : AuthorizationIntent()
    data object RememberPassword: AuthorizationIntent()
    data object DismissAllCoroutines : AuthorizationIntent()
}

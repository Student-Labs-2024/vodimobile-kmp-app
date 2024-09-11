package com.vodimobile.presentation.screens.registration.store

sealed class RegistrationEffect {
    data object OpenUserAgreement : RegistrationEffect()
    data object SmsVerification : RegistrationEffect()
    data object ReturnBack : RegistrationEffect()
    data object AskPermission : RegistrationEffect()
    data object SupabaseAuthUserError : RegistrationEffect()
    data object NotUniquePhone : RegistrationEffect()
}

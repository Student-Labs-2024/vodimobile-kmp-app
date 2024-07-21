package com.vodimobile.presentation.screens.registration.store

sealed class RegistrationIntent {
    data object OpenUserAgreement : RegistrationIntent()
    data object SmsVerification : RegistrationIntent()
    data object ReturnBack : RegistrationIntent()
    data class EmailChange(val value: String) : RegistrationIntent()
    data class PhoneNumberChange(val value: String) : RegistrationIntent()
}

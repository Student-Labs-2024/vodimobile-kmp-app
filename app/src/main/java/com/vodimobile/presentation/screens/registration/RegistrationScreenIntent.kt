package com.vodimobile.presentation.screens.registration

sealed class RegistrationScreenIntent {
    data object OpenUserAgreement : RegistrationScreenIntent()
    data object SmsVerification : RegistrationScreenIntent()
    data object ReturnBack : RegistrationScreenIntent()
    data class EmailChange(val value: String) : RegistrationScreenIntent()
    data class PhoneNumberChange(val value: String) : RegistrationScreenIntent()
}

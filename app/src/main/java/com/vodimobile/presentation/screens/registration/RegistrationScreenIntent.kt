package com.vodimobile.presentation.screens.registration

sealed class RegistrationScreenIntent {
    data object OpenUserAgreement : RegistrationScreenIntent()
    data object SmsVerification : RegistrationScreenIntent()
    data object ReturnBack : RegistrationScreenIntent()
}

package com.vodimobile.presentation.screens.registration.store

sealed class RegistrationOutput {
    data object OpenUserAgreement : RegistrationOutput()
    data object SmsVerification : RegistrationOutput()
    data object ReturnBack : RegistrationOutput()
}
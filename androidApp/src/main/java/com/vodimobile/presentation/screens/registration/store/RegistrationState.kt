package com.vodimobile.presentation.screens.registration.store

data class RegistrationState(
    val email: String = "",
    val phoneNumber: String = "",
    val emailError: Boolean = true,
    val phoneNumberError: Boolean = true
)

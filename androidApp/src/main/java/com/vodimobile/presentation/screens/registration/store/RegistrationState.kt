package com.vodimobile.presentation.screens.registration.store

data class RegistrationState(
    val name: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val nameError: Boolean = true,
    val phoneNumberError: Boolean = true,
    val passwordError: Boolean = true
)

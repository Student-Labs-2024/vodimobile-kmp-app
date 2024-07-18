package com.vodimobile.presentation.screens.registration.store

data class RegistrationFieldsState(
    val email: String = "",
    val phoneNumber: String = "",
    val emailError: Boolean = true,
    val phoneNumberError: Boolean = true
)

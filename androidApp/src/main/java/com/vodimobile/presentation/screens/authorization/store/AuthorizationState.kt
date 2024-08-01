package com.vodimobile.presentation.screens.authorization.store

data class AuthorizationState(
    val phoneNumber: String = "",
    val password: String = "",
    val phoneNumberError: Boolean = true,
    val passwordError: Boolean = true
)

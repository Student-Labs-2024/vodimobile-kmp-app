package com.vodimobile.presentation.screens.reset_password.store

data class ResetPasswordState(
    val phoneNumber: String = "",
    val phoneNumberError: Boolean = true
)

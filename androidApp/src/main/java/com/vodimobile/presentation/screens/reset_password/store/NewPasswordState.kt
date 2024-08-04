package com.vodimobile.presentation.screens.reset_password.store

data class NewPasswordState(
    val password: String = "",
    val passwordError: Boolean = true
)

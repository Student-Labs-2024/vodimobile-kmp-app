package com.vodimobile.presentation.screens.change_password.store

sealed class ChangePasswordIntent {
    data object SaveChanges : ChangePasswordIntent()
    data object ReturnBack : ChangePasswordIntent()
    data object RememberPassword : ChangePasswordIntent()
    data class OldPasswordChange(val value: String) : ChangePasswordIntent()
    data class NewPasswordChange(val value: String) : ChangePasswordIntent()
}

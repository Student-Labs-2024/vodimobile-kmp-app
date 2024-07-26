package com.vodimobile.presentation.screens.change_password.store

sealed class ChangePasswordEffect {
    data object SaveChanges : ChangePasswordEffect()
    data object ReturnBack : ChangePasswordEffect()
    data object RememberPassword : ChangePasswordEffect()
}

package com.vodimobile.presentation.screens.logout

sealed class LogOutEffect {
    data object LogOut : LogOutEffect()
    data object Cansel : LogOutEffect()
}
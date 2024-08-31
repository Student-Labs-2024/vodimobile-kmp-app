package com.vodimobile.presentation.screens.logout

sealed class LogOutIntent {
    data object LogOut : LogOutIntent()
    data object Cansel : LogOutIntent()
}
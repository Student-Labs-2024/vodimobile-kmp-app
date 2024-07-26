package com.vodimobile.presentation.screens.home.store

sealed class HomeIntent {
    data object FieldClick : HomeIntent()
    data object NotificationButtonClick : HomeIntent()
    data object CarPreviewClick : HomeIntent()
    data object AllCarsClick : HomeIntent()
}
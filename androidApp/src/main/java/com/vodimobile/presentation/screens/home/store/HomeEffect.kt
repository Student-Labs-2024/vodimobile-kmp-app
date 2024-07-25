package com.vodimobile.presentation.screens.home.store

sealed class HomeEffect {
    data object FieldClick : HomeEffect()
    data object NotificationButtonClick : HomeEffect()
    data object CarPreviewClick : HomeEffect()
}
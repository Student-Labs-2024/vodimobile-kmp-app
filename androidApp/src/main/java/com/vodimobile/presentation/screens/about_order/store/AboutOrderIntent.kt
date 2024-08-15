package com.vodimobile.presentation.screens.about_order.store

sealed class AboutOrderIntent {
    data object BackClick : AboutOrderIntent()
    data object ChangeOrderClick : AboutOrderIntent()
    data object CanselOrder : AboutOrderIntent()
}
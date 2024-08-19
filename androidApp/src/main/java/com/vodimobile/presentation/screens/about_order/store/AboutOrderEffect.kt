package com.vodimobile.presentation.screens.about_order.store

sealed class AboutOrderEffect {
    data object BackClick : AboutOrderEffect()
    data object ShowDialog : AboutOrderEffect()
    data object DismissDialog : AboutOrderEffect()
    data object ChangeOrderClick : AboutOrderEffect()
}
package com.vodimobile.presentation.screens.about_order.store

sealed class AboutOrderIntent {
    data object BackClick : AboutOrderIntent()
    data object ChangeOrderClick : AboutOrderIntent()
    data object CanselOrder : AboutOrderIntent()
    data class InitOrder(val orderId: Int) : AboutOrderIntent()
    data object CanselCoroutines : AboutOrderIntent()
}

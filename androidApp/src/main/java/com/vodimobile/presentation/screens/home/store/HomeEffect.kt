package com.vodimobile.presentation.screens.home.store

sealed class HomeEffect {
    data object FieldClick : HomeEffect()
    data object NotificationButtonClick : HomeEffect()
    data object AllCarsClick : HomeEffect()
    data class BookCarClick(val carId: Int) : HomeEffect()
    data object UnauthedUser : HomeEffect()
}

package com.vodimobile.presentation.screens.home.store

import com.vodimobile.domain.model.Car

sealed class HomeIntent {
    data object FieldClick : HomeIntent()
    data object NotificationButtonClick : HomeIntent()
    data class ShowModal(val car: Car) : HomeIntent()
    data object CloseModal : HomeIntent()
    data object AllCarsClick : HomeIntent()
    data class BookCarClick(val carId: Int) : HomeIntent()
    data object InitUser : HomeIntent()
}
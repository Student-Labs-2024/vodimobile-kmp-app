package com.vodimobile.presentation.screens.home.store

import com.vodimobile.domain.model.Car

data class HomeState(
    val selectedDate: LongArray = longArrayOf(System.currentTimeMillis(), System.currentTimeMillis()),
    val carList: List<Car> = emptyList(),
    val selectedCar: Car = Car.empty(),
    val showBottomSheet: Boolean = false
)

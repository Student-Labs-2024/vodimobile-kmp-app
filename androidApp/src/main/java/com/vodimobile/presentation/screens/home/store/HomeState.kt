package com.vodimobile.presentation.screens.home.store

import com.vodimobile.domain.model.Car

data class HomeState(
    val selectedDate: Long = 0L,
    val carList: List<Car> = emptyList()
)
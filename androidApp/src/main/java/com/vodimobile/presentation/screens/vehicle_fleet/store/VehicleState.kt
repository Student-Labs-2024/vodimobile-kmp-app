package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.domain.model.Car

data class VehicleState (
    val carList: List<Car> = emptyList(),
    val selectedCar: Car = Car.empty(),
)
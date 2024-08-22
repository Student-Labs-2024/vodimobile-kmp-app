package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.domain.model.Car

sealed class VehicleIntent {
    data object BackClick : VehicleIntent()
    data class OnSelected(val value: Int) : VehicleIntent()
    data class BookCarClick(val car: Car) : VehicleIntent()
    data class InfoCarClick(val car: Car) : VehicleIntent()
    data object CloseModal : VehicleIntent()
    data class InitCars(val dateRange: LongArray) : VehicleIntent()
    data object ShowProgressDialog : VehicleIntent()
    data object DismissProgressDialog : VehicleIntent()
    data class InitCarList(val value: List<Car>) : VehicleIntent()
}
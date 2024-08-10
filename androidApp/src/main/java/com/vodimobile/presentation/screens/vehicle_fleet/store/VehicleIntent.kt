package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse

sealed class VehicleIntent {
    data object BackClick : VehicleIntent()
    data object OnSelected : VehicleIntent()
    data class BookCarClick(val car: Car) : VehicleIntent()
    data class InfoCarClick(val car: Car) : VehicleIntent()
    data object CloseModal : VehicleIntent()
    data object InitCars : VehicleIntent()
    data object ShowProgressDialog : VehicleIntent()
    data object DismissProgressDialog : VehicleIntent()
    data object AuthUser : VehicleIntent()
    data class InitUser(val userResponse: UserResponse) : VehicleIntent()
}
package com.vodimobile.presentation.screens.vehicle_fleet.store

sealed class VehicleEffect {
    data object BackClick : VehicleEffect()
    data class BookCarClick(val carId: Int) : VehicleEffect()
    data object DismissLoadingDialog : VehicleEffect()
    data object ShowLoadingDialog : VehicleEffect()
    data object ServerError : VehicleEffect()
    data object UnauthedUser : VehicleEffect()
}

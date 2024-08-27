package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.domain.model.Car

sealed class VehicleIntent {
    data object BackClick : VehicleIntent()
    data class OnSelected(val value: Int) : VehicleIntent()
    data class BookCarClick(val car: Car) : VehicleIntent()
    data class ShowModal(val car: Car) : VehicleIntent()
    data object CloseModal : VehicleIntent()
    data class InitCars(val dateRange: LongArray) : VehicleIntent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as InitCars

            return dateRange.contentEquals(other.dateRange)
        }

        override fun hashCode(): Int {
            return dateRange.contentHashCode()
        }
    }

    data object ShowProgressDialog : VehicleIntent()
    data object DismissProgressDialog : VehicleIntent()
    data class InitCarList(val value: List<Car>) : VehicleIntent()
    data object CancelCoroutines : VehicleIntent()
    data class InitDateRange(val dateRange: LongArray) : VehicleIntent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as InitDateRange

            return dateRange.contentEquals(other.dateRange)
        }

        override fun hashCode(): Int {
            return dateRange.contentHashCode()
        }
    }
}

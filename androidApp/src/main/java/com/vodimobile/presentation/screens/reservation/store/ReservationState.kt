package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Place

data class ReservationState(
    val placeList: List<Place> = listOf(),
    val bidCost: String = "",
    val place: String = "",
    val placeId: Int = 0,
    val errorPlace: Boolean = true,
    val date: LongArray = longArrayOf(0L, 0L),
    val errorDate: Boolean = true,
    val startTime: String = "",
    val errorStartTime: Boolean = true,
    val endTime: String = "",
    val errorEndTime: Boolean = true,
    val comments: String = "",
    val carId: Int = 0,
    val selectedCar: Car = Car.empty(),
    val freeDates: List<Pair<Long, Long>> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReservationState

        if (placeList != other.placeList) return false
        if (bidCost != other.bidCost) return false
        if (place != other.place) return false
        if (placeId != other.placeId) return false
        if (errorPlace != other.errorPlace) return false
        if (!date.contentEquals(other.date)) return false
        if (errorDate != other.errorDate) return false
        if (startTime != other.startTime) return false
        if (errorStartTime != other.errorStartTime) return false
        if (endTime != other.endTime) return false
        if (errorEndTime != other.errorEndTime) return false
        if (comments != other.comments) return false
        if (carId != other.carId) return false
        if (selectedCar != other.selectedCar) return false
        if (freeDates != other.freeDates) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeList.hashCode()
        result = 31 * result + bidCost.hashCode()
        result = 31 * result + place.hashCode()
        result = 31 * result + placeId
        result = 31 * result + errorPlace.hashCode()
        result = 31 * result + date.contentHashCode()
        result = 31 * result + errorDate.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + errorStartTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + errorEndTime.hashCode()
        result = 31 * result + comments.hashCode()
        result = 31 * result + carId
        result = 31 * result + selectedCar.hashCode()
        result = 31 * result + freeDates.hashCode()
        return result
    }

}

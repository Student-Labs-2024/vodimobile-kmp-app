package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Place
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.service_list.ServicesDTO

data class ReservationState(
    val placeList: List<Place> = emptyList(),
    val serviceList: List<ServicesDTO> = emptyList(),
    val bidCost: String = "",
    val getPlace: String = "",
    val getPlaceId: Int = 0,
    val errorGetPlace: Boolean = true,
    val returnPlace: String = "",
    val returnPlaceId: Int = 0,
    val errorReturnPlace: Boolean = true,
    val date: LongArray = longArrayOf(0L, 0L),
    val errorDate: Boolean = true,
    val startTime: String = "",
    val errorStartTime: Boolean = true,
    val endTime: String = "",
    val errorEndTime: Boolean = true,
    val selectedServiceIdList: List<ServicesDTO> = emptyList(),
    val carId: Int = 0,
    val selectedCar: Car = Car.empty(),
    val freeDates: List<Pair<Long, Long>> = emptyList(),
    val user: User = User.empty()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReservationState

        if (placeList != other.placeList) return false
        if (serviceList != other.serviceList) return false
        if (bidCost != other.bidCost) return false
        if (getPlace != other.getPlace) return false
        if (getPlaceId != other.getPlaceId) return false
        if (errorGetPlace != other.errorGetPlace) return false
        if (returnPlace != other.returnPlace) return false
        if (returnPlaceId != other.returnPlaceId) return false
        if (errorReturnPlace != other.errorReturnPlace) return false
        if (!date.contentEquals(other.date)) return false
        if (errorDate != other.errorDate) return false
        if (startTime != other.startTime) return false
        if (errorStartTime != other.errorStartTime) return false
        if (endTime != other.endTime) return false
        if (errorEndTime != other.errorEndTime) return false
        if (selectedServiceIdList != other.selectedServiceIdList) return false
        if (carId != other.carId) return false
        if (selectedCar != other.selectedCar) return false
        if (freeDates != other.freeDates) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeList.hashCode()
        result = 31 * result + serviceList.hashCode()
        result = 31 * result + bidCost.hashCode()
        result = 31 * result + getPlace.hashCode()
        result = 31 * result + getPlaceId
        result = 31 * result + errorGetPlace.hashCode()
        result = 31 * result + returnPlace.hashCode()
        result = 31 * result + returnPlaceId
        result = 31 * result + errorReturnPlace.hashCode()
        result = 31 * result + date.contentHashCode()
        result = 31 * result + errorDate.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + errorStartTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + errorEndTime.hashCode()
        result = 31 * result + selectedServiceIdList.hashCode()
        result = 31 * result + carId
        result = 31 * result + selectedCar.hashCode()
        result = 31 * result + freeDates.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }
}

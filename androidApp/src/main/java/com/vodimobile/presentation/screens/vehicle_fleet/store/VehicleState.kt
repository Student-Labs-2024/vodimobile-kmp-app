package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.android.R
import com.vodimobile.domain.model.Car

data class VehicleState(
    val selectedCar: Car = Car.empty(),
    val showBottomSheet: Boolean = false,
    val dateRange: LongArray = longArrayOf(),
    val cars: List<Car> = emptyList(),
    val filteredCars: List<Car> = emptyList(),
    val isLoading: Boolean = false,
    val selectedIndex: Int = 0,

    val tags: List<Int> = listOf(
        R.string.auto_tag_all,
        R.string.auto_tag_economy,
        R.string.auto_tag_comfort,
        R.string.auto_tag_premium,
        R.string.auto_tag_sedan,
        R.string.auto_tag_off_road
    )
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleState

        if (selectedCar != other.selectedCar) return false
        if (showBottomSheet != other.showBottomSheet) return false
        if (!dateRange.contentEquals(other.dateRange)) return false
        if (cars != other.cars) return false
        if (filteredCars != other.filteredCars) return false
        if (isLoading != other.isLoading) return false
        if (selectedIndex != other.selectedIndex) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedCar.hashCode()
        result = 31 * result + showBottomSheet.hashCode()
        result = 31 * result + dateRange.contentHashCode()
        result = 31 * result + cars.hashCode()
        result = 31 * result + filteredCars.hashCode()
        result = 31 * result + isLoading.hashCode()
        result = 31 * result + selectedIndex
        result = 31 * result + tags.hashCode()
        return result
    }
}

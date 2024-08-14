package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.android.R
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.either.CrmEither
import io.ktor.http.HttpStatusCode

data class VehicleState(
    val selectedCar: Car = Car.empty(),
    val showBottomSheet: Boolean = false,
    val dateRange: LongArray = longArrayOf(),
    val carList: List<Car> = emptyList(),

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
        if (carList != other.carList) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedCar.hashCode()
        result = 31 * result + showBottomSheet.hashCode()
        result = 31 * result + dateRange.contentHashCode()
        result = 31 * result + carList.hashCode()
        result = 31 * result + tags.hashCode()
        return result
    }
}
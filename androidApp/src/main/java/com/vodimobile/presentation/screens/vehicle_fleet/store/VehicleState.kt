package com.vodimobile.presentation.screens.vehicle_fleet.store

import com.vodimobile.android.R
import com.vodimobile.domain.model.Car

data class VehicleState(
    val carList: List<Car> = emptyList(),
    val selectedCar: Car = Car.empty(),
    val showBottomSheet: Boolean = false,

    val tags: List<Int> = listOf(
        R.string.auto_tag_all,
        R.string.auto_tag_economy,
        R.string.auto_tag_comfort,
        R.string.auto_tag_premium,
        R.string.auto_tag_sedan,
        R.string.auto_tag_off_road
    )
)
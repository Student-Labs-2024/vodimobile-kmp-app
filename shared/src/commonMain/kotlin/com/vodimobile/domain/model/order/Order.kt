package com.vodimobile.domain.model.order

import com.vodimobile.domain.model.Car
import dev.icerock.moko.resources.StringResource

data class Order(
    val model: StringResource,
    val status: CarStatus,
    val rentalPeriod: DateRange,
    val pickupLocation: String,
    val pickupTime: TimeRange,
    val specifications: Car
)
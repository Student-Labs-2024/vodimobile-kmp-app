package com.vodimobile.domain.model.order

import dev.icerock.moko.resources.StringResource

data class Order(
    val model: StringResource,
    val status: CarStatus,
    val rentalPeriod: DateRange,
    val pickupLocation: String,
    val pickupTime: Time,
    val year: Int?,
    val transmission: StringResource,
    val wheelDrive: StringResource,
    val tankValue: StringResource,
    val totalCost: Double,
)

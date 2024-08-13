package com.vodimobile.domain.model.order

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.Car

data class Order(
    val userId: Int,
    val bidNumber: Int,
    val bid: Bid,
    val status: CarStatus,
    val rentalDatePeriod: DateRange,
    val startLocation: String,
    val finishLocation: String,
    val rentalTimePeriod: TimeRange,
    val car: Car,
    val services: Array<String>
)

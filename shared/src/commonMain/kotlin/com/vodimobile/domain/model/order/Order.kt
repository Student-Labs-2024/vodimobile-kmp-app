package com.vodimobile.domain.model.order

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.CarType
import com.vodimobile.domain.model.Tariff
import com.vodimobile.shared.resources.SharedRes
import dev.icerock.moko.resources.StringResource

data class Order(
    val status: CarStatus,
    val rentalPeriod: DateRange,
    val pickupLocation: String,
    val pickupTime: TimeRange,
    val specifications: Car = Car.empty()
){
    companion object {
        fun empty(): Order{
            return Order (
                status = CarStatus.Cancelled,
                rentalPeriod = DateRange(startDate =  1688236800000, endDate = 1690655999000 ),
                pickupLocation = "",
                pickupTime = TimeRange(startTime = 3, finishTime = 4)
            )
        }
    }
}

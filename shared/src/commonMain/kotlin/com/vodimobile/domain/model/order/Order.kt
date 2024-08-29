package com.vodimobile.domain.model.order

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.Car
import kotlinx.datetime.Clock

data class Order(
    val userId: Int,
    val orderId: Int,
    val bidNumber: Int,
    val bid: Bid,
    val status: CarStatus,
    val rentalDatePeriod: DateRange,
    val startLocation: String,
    val finishLocation: String,
    val rentalTimePeriod: TimeRange,
    val car: Car,
    val services: Array<String>
) {
    companion object {
        fun empty() : Order {
            return Order(
                userId = 0,
                orderId = 0,
                bidNumber = 0,
                bid = Bid(
                    cost = 0.0,
                    prepay = 0.0,
                    deposit = 0.0,
                    errorMessage = ""
                ),
                status = CarStatus.Processing,
                rentalDatePeriod = DateRange(startDate = Clock.System.now().epochSeconds, endDate = Clock.System.now().epochSeconds),
                startLocation = "",
                finishLocation = "",
                rentalTimePeriod = TimeRange(startTime = "12:00", finishTime = "14:00"),
                car = Car.empty(),
                services = arrayOf("1","2","3")
            )
        }
    }
}

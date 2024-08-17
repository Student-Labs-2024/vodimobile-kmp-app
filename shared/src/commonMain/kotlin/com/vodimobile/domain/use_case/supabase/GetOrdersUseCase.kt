package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.order.DateRange
import com.vodimobile.domain.model.order.Order
import com.vodimobile.domain.model.order.TimeRange
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.utils.array_parser.toStringArray
import com.vodimobile.utils.car_status.toCarStatus
import com.vodimobile.utils.date_formats.parseDateToLong
import com.vodimobile.utils.date_formats.parseTimeToLong

class GetOrdersUseCase(
    private val supabaseRepository: SupabaseRepository,
    private val crmStorage: CrmStorage
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        userId: Int
    ): List<Order> {

        val carsEither = crmStorage.getCarList(accessToken, refreshToken)
        val orderList = supabaseRepository.getOrders(userId = userId)
        val carIdes: List<Int> = orderList.map { it.car_id }

        val cars: List<Car> = when (carsEither) {
            is CrmEither.CrmData -> {
                carsEither.data.filter { it.carId in carIdes }
            }

            is CrmEither.CrmError -> {
                emptyList()
            }

            CrmEither.CrmLoading -> {
                emptyList()
            }
        }

        return orderList.map { orderDTO ->
            Order(
                userId = userId,
                bidNumber = orderDTO.bid_id,
                bid = Bid(
                    cost = orderDTO.cost.toDouble(),
                    prepay = 0.0,
                    deposit = 0.0,
                    errorMessage = null
                ),
                status = orderDTO.bid_status.toCarStatus(),
                rentalDatePeriod = DateRange(
                    startDate = orderDTO.date_start.parseDateToLong(),
                    endDate = orderDTO.date_finish.parseDateToLong()
                ),
                startLocation = orderDTO.place_start,
                finishLocation = orderDTO.place_finish,
                rentalTimePeriod = TimeRange(
                    startTime = orderDTO.time_start.parseTimeToLong(),
                    finishTime = orderDTO.time_finish.parseTimeToLong()
                ),
                car = cars.single { it.carId == orderDTO.car_id },
                services = orderDTO.services.toStringArray()
            )
        }
    }
}
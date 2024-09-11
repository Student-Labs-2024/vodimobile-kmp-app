package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.order.CarStatus
import com.vodimobile.domain.model.order.DateRange
import com.vodimobile.domain.model.order.Order
import com.vodimobile.domain.model.order.TimeRange
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.utils.array_parser.toStringArray
import com.vodimobile.utils.bid.bidGripReverse
import com.vodimobile.utils.bid.crmBidGrid
import com.vodimobile.utils.car_status.toCarStatus
import com.vodimobile.utils.date_formats.parseDateToLong
import com.vodimobile.utils.date_formats.parseTimeToLong
import kotlinx.coroutines.job
import kotlin.coroutines.coroutineContext

class GetOrdersUseCase(
    private val supabaseRepository: SupabaseRepository,
    private val crmStorage: CrmStorage,
    private val crmRepository: CrmRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        userId: Int,
        phone: String
    ): List<Order> {

        val carsEither = crmStorage.getCarList(accessToken, refreshToken)
        val orderList = supabaseRepository.getOrders(userId = userId)
        val bids = mutableListOf<String>()
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

        orderList.forEach {
            val bidEither = crmRepository.getBidStatus(
                accessToken,
                refreshToken,
                phone = phone,
                bidNumber = it.bid_number
            )
            val bidStatus = when (bidEither) {
                is CrmEither.CrmData -> {
                    crmBidGrid[bidEither.data.bid_status_name] ?: CarStatus.Cancelled
                }

                is CrmEither.CrmError -> {
                    CarStatus.Cancelled
                }

                CrmEither.CrmLoading -> {
                    CarStatus.Cancelled
                }
            }
            bids.add(bidGripReverse[bidStatus] ?: "В обработке")
            supabaseRepository.updateOrderStatus(
                userId = userId,
                orderId = it.bid_id,
                status = (bidGripReverse[bidStatus] ?: "В обработке")
            )
        }

        return orderList.mapIndexed { index, orderDTO ->
            Order(
                userId = userId,
                orderId = orderDTO.bid_id,
                bidNumber = orderDTO.bid_number,
                bid = Bid(
                    cost = orderDTO.cost.toDouble(),
                    prepay = 0.0,
                    deposit = 0.0,
                    errorMessage = null
                ),
                status = bids[index].toCarStatus(),
                rentalDatePeriod = DateRange(
                    startDate = orderDTO.date_start.parseDateToLong(),
                    endDate = orderDTO.date_finish.parseDateToLong()
                ),
                startLocation = orderDTO.place_start,
                finishLocation = orderDTO.place_finish,
                rentalTimePeriod = TimeRange(
                    startTime = orderDTO.time_start,
                    finishTime = orderDTO.time_finish
                ),
                car = cars.single { it.carId == orderDTO.car_id },
                services = orderDTO.services.toStringArray()
            )
        }
    }
}

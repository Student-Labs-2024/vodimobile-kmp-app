package com.vodimobile.di

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    try {
        startKoin {
            // Declare modules
            modules(carModule, crmModule, supabaseModule)
        }
    } catch (e: Exception) {
        // Handle or log the exception
        println("Error initializing Koin: ${e.message}")
    }
}

class KoinHelper : KoinComponent {
    private val carsStorage by inject<CarsStorage>()
    private val crmStorage by inject<CrmStorage>()
    private val supabaseStorage by inject<SupabaseStorage>()

    fun getPopularCars(): List<Car> = carsStorage.getPopularCars()

    suspend fun getCars(accessToken: String, refreshToken: String) =
        crmStorage.getCarList(accessToken = accessToken, refreshToken = refreshToken)

    suspend fun getTariffByCar(carId: Int, accessToken: String, refreshToken: String) =
        crmStorage.getTariffByCar(
            carId = carId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )

    suspend fun postUser() = crmStorage.authUser()

    suspend fun getPlaces(accessToken: String, refreshToken: String) =
        crmStorage.getPlaces(accessToken = accessToken, refreshToken = refreshToken)

    suspend fun getFreeCars(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ) = crmStorage.getFreeCars(accessToken, refreshToken, carFreeListParamsDTO)

    suspend fun getServices(accessToken: String, refreshToken: String) =
        crmStorage.getServices(accessToken, refreshToken)

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest) =
        crmStorage.refreshToken(refreshTokenRequest)

    suspend fun getBidCost(
        accessToken: String,
        refreshToken: String,
        bidCostParams: BidCostParams
    ) = crmStorage.getBidCost(accessToken, refreshToken, bidCostParams)


    suspend fun getUser(password: String, phone: String) = supabaseStorage.getUser(password, phone)
    suspend fun insertUser(user: User) = supabaseStorage.insertUser(user = user)
    suspend fun updatePhone(userId: Int, phone: String) = supabaseStorage.updatePhone(userId, phone)
    suspend fun updatePassword(userId: Int, password: String) =
        supabaseStorage.updatePassword(userId, password)

    suspend fun updateFullName(userId: Int, fullName: String) =
        supabaseStorage.updateFullName(userId, fullName)

    suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String) =
        supabaseStorage.updateTokens(userId, accessToken, refreshToken)

    suspend fun insertOrder(orderDTO: OrderDTO) =
        supabaseStorage.insertOrder(orderDTO = orderDTO)

    suspend fun getOrders(userId: Int, accessToken: String, refreshToken: String, phone: String) =
        supabaseStorage.getOrders(userId = userId, accessToken = accessToken, refreshToken = refreshToken, phone = phone)

    suspend fun updateStatus(userId: Int, orderId: Int, status: String) =
        supabaseStorage.updateStatus(userId, orderId, status)

    suspend fun updateNumber(userId: Int, orderId: Int, number: Int) =
        supabaseStorage.updateNumber(userId, orderId, number)

    suspend fun updateCrmOrder(userId: Int, orderId: Int, crmOrder: Int) =
        supabaseStorage.updateCrmOrder(userId, orderId, crmOrder)

    suspend fun updatePlaceStart(userId: Int, orderId: Int, placeStart: String) =
        supabaseStorage.updatePlaceStart(userId, orderId, placeStart)

    suspend fun updatePlaceFinish(userId: Int, orderId: Int, placeFinish: String) =
        supabaseStorage.updatePlaceFinish(userId, orderId, placeFinish)

    suspend fun updateCost(userId: Int, orderId: Int, coast: Float) =
        supabaseStorage.updateCost(userId, orderId, coast)

    suspend fun updateServices(userId: Int, orderId: Int, services: String) =
        supabaseStorage.updateServices(userId, orderId, services)
}

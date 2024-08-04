package com.vodimobile.di

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.storage.crm.CrmStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(carModule, crmModule)
    }
}

class KoinHelper : KoinComponent {
    private val carsStorage by inject<CarsStorage>()
    private val crmStorage by inject<CrmStorage>()

    fun getPopularCars(): List<Car> = carsStorage.getPopularCars()

    suspend fun getCars(accessToken: String, refreshToken: String) =
        crmStorage.getCarList(accessToken = accessToken, refreshToken = refreshToken)

    suspend fun getTariffByCar(carId: Int, accessToken: String, refreshToken: String) =
        crmStorage.getTariffByCar(
            carId = carId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )

    suspend fun postUser(userRequest: UserRequest) = crmStorage.authUser(userRequest = userRequest)

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
}

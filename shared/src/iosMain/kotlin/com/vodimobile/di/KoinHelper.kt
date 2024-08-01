package com.vodimobile.di

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.storage.crm.CrmStorage
import io.ktor.client.plugins.auth.providers.BearerTokens
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

    suspend fun getCars(bearerTokens: BearerTokens) = crmStorage.getCarList(bearerTokens = bearerTokens)
    suspend fun getTariffByCar(carId: Int) = crmStorage.getTariffByCar(carId = carId)
    suspend fun postUser(userRequest: UserRequest) = crmStorage.authUser(userRequest = userRequest)
}

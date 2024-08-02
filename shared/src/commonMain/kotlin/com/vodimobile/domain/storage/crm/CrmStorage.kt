package com.vodimobile.domain.storage.crm

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Tariff
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.http.HttpStatusCode

class CrmStorage(
    private val getCarListUseCase: GetCarListUseCase,
    private val getTariffListUseCase: GetTariffListUseCase,
    private val postNewUserUseCase: PostNewUserUseCase,
    private val getAllPlacesUseCase: GetAllPlacesUseCase,
    private val getFreeCarsUseCaSE: GetFreeCarsUseCaSE
) {
    suspend fun getCarList(
        accessToken: String,
        refreshToken: String
    ): CrmEither<List<Car>, HttpStatusCode> {
        return getCarListUseCase(accessToken = accessToken, refreshToken = refreshToken)
    }

    suspend fun getTariffByCar(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode> {
        return getTariffListUseCase(
            carId = carId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    suspend fun authUser(userRequest: UserRequest): CrmEither<UserResponse, HttpStatusCode> {
        return postNewUserUseCase(userRequest = userRequest)
    }

    suspend fun getPlaces(
        accessToken: String,
        refreshToken: String
    ) = getAllPlacesUseCase.invoke(
        accessToken,
        refreshToken
    )

    suspend fun getFreeCars(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ) = getFreeCarsUseCaSE.invoke(accessToken, refreshToken, carFreeListParamsDTO)
}
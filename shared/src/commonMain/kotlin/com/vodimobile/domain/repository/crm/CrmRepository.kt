package com.vodimobile.domain.repository.crm

import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.http.HttpStatusCode

interface CrmRepository {
    suspend fun getCarList(
        accessToken: String,
        refreshToken: String
    ): CrmEither<CarListDTO, HttpStatusCode>

    suspend fun getTariffList(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode>

    suspend fun postNewUser(userRequest: UserRequest): CrmEither<UserResponse, HttpStatusCode>
}
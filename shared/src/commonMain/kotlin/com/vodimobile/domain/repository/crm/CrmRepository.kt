package com.vodimobile.domain.repository.crm

import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import io.ktor.http.HttpStatusCode

interface CrmRepository {
    suspend fun getCarList(): CrmEither<CarListDTO, HttpStatusCode>
}
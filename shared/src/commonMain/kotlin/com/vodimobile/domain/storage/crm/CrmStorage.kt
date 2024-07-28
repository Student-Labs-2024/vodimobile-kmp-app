package com.vodimobile.domain.storage.crm

import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import io.ktor.http.HttpStatusCode

class CrmStorage(
    private val getCarListUseCase: GetCarListUseCase
) {
    suspend fun getCarList(): CrmEither<CarListDTO, HttpStatusCode> {
        return getCarListUseCase()
    }
}
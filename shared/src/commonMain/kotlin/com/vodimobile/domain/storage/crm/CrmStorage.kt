package com.vodimobile.domain.storage.crm

import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import io.ktor.http.HttpStatusCode

class CrmStorage(
    private val getCarListUseCase: GetCarListUseCase,
    private val getTariffListUseCase: GetTariffListUseCase
) {
    suspend fun getCarList(): CrmEither<CarListDTO, HttpStatusCode> {
        return getCarListUseCase()
    }

    suspend fun getTariffByCar(carId: Int): CrmEither<TariffListDTO, HttpStatusCode> {
        return getTariffListUseCase(carId = carId)
    }
}
package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode

class GetCarListUseCase(
    private val crmRepository: CrmRepository
) {
    suspend operator fun invoke(): CrmEither<CarListDTO, HttpStatusCode> {
        return crmRepository.getCarList()
    }
}
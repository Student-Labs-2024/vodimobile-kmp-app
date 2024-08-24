package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListDTO
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetFreeCarsUseCaSE(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ): CrmEither<CarFreeListDTO, HttpStatusCode> {
        delay(400.milliseconds)
        val carFreeListDTOHttpStatusCodeCrmEither: CrmEither<CarFreeListDTO, HttpStatusCode> =
            crmRepository.getFreeCars(accessToken, refreshToken, carFreeListParamsDTO)

        return carFreeListDTOHttpStatusCodeCrmEither
    }
}

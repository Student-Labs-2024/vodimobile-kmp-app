package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetTariffListUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode> {
        val either = crmRepository.getTariffList(
            carId = carId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )

        return either
    }
}

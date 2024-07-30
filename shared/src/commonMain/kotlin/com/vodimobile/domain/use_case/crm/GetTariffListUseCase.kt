package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.Tariff
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO.Companion.toTariff
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode

class GetTariffListUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(carId: Int): CrmEither<List<Tariff>, HttpStatusCode> {
        val either = crmRepository.getTariffList(carId = carId)
        val returnEither = CrmEither(
            data = either.data?.toTariff(),
            status = either.status
        )

        return returnEither
    }
}
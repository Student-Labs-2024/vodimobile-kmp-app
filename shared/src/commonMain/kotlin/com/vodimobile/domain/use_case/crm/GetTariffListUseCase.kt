package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.repository.crm.CrmRepository

class GetTariffListUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(carId: Int) =
        crmRepository.getTariffList(carId = carId)
}
package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.repository.crm.CrmRepository

class GetServiceListUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) =
        crmRepository.getAllServices(accessToken, refreshToken)

}

package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.repository.crm.CrmRepository

class RefreshTokenUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(refreshTokenRequest: RefreshTokenRequest) =
        crmRepository.refreshToken(refreshTokenRequest)
}

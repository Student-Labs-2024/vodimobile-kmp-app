package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.repository.crm.CrmRepository

class PostNewUserUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke() =
        crmRepository.postNewUser()
}
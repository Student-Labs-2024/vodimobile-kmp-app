package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.service_list.ServicesDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetServiceListUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): CrmEither<List<ServicesDTO>, HttpStatusCode> {
//        delay(400.milliseconds)
        return crmRepository.getAllServices(accessToken, refreshToken)
    }
}

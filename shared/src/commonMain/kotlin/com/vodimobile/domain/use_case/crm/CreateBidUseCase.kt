package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateDTO
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class CreateBidUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        bidCreateParams: BidCreateParams
    ): CrmEither<BidCreateDTO, HttpStatusCode> {
        delay(1.seconds)
        return crmRepository.createBid(accessToken, refreshToken, bidCreateParams)
    }
}

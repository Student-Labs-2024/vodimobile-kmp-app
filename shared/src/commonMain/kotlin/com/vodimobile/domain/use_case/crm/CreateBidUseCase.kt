package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateDTO
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode

class CreateBidUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        bidCreateParams: BidCreateParams
    ): CrmEither<BidCreateDTO, HttpStatusCode> {
        return crmRepository.createBid(accessToken, refreshToken, bidCreateParams)
    }
}

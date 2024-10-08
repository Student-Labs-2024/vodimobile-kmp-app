package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostDTO
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetBidCostUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        bidCostParams: BidCostParams
    ): CrmEither<Bid, HttpStatusCode> {
        val bidCost: CrmEither<BidCostDTO, HttpStatusCode> =
            crmRepository.getBidCost(accessToken, refreshToken, bidCostParams)

        return when (bidCost) {
            is CrmEither.CrmData -> {
                with(bidCost.data) {
                    CrmEither.CrmData(
                        data = Bid(
                            cost = cost ?: 0.0,
                            deposit = deposit ?: 0.0,
                            prepay = prepay ?: 0.0,
                            errorMessage = error_message
                        )
                    )
                }
            }

            is CrmEither.CrmError -> {
                CrmEither.CrmError(status = bidCost.status)
            }

            CrmEither.CrmLoading -> {
                CrmEither.CrmLoading
            }
        }
    }
}

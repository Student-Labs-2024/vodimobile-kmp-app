package com.vodimobile.domain.model.remote.dto.bid_cost

import kotlinx.serialization.Serializable

@Serializable
data class BidCostDTO(
    val result_code: Int,
    val cost: Double,
    val prepay: Double,
    val deposit: Double,
    val error_message: String?
)

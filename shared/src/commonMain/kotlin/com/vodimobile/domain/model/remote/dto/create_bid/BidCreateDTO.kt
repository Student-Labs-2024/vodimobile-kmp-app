package com.vodimobile.domain.model.remote.dto.create_bid

import kotlinx.serialization.Serializable

@Serializable
data class BidCreateDTO(
    val result_code: Int,
    val bid_id: Int?,
    val bid_number: Int?,
    val error_message: String?
)

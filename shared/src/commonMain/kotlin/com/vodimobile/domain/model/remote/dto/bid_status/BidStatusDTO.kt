package com.vodimobile.domain.model.remote.dto.bid_status

import kotlinx.serialization.Serializable

@Serializable
data class BidStatusDTO(
    val result_code: Int,
    val bid_id: Int,
    val bid_status_name: String,
    val bid_status_title: String
)

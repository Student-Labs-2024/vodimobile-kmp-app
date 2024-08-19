package com.vodimobile.domain.model.remote.dto.bid_cost

import kotlinx.serialization.Serializable

@Serializable
data class BidCostParams(
    val car_id: Int,
    val begin: String,
    val end: String,
    val begin_place_id: Int,
    val end_place_id: Int,
    val services: Array<Int>?
)

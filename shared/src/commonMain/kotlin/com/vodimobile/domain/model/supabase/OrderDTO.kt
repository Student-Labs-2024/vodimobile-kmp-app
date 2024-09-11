package com.vodimobile.domain.model.supabase

import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val bid_id: Int,
    val bid_number: Int,
    val user_id: Int,
    val crm_bid_id: Int,
    val car_id: Int,
    val bid_status: String,
    val date_start: String,
    val time_start: String,
    val date_finish: String,
    val time_finish: String,
    val place_start: String,
    val place_finish: String,
    val cost: Float,
    val services: String
)

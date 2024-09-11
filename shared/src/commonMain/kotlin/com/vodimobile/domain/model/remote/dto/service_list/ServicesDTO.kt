package com.vodimobile.domain.model.remote.dto.service_list

import kotlinx.serialization.Serializable

@Serializable
data class ServicesDTO(
    val service_id: Int,
    val title: String,
    val cost: Double,
    val service_type_id: Int,
    val archive: Boolean
)

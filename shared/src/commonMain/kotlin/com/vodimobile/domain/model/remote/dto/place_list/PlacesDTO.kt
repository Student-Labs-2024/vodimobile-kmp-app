package com.vodimobile.domain.model.remote.dto.place_list

import kotlinx.serialization.Serializable

@Serializable
data class PlacesDTO(
    val place_id: Int,
    val title: String,
    val city_id: Int,
    val delivery_cost: Float,
    val archive: Boolean
)

package com.vodimobile.domain.model.remote.dto.place_list

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDTO(
    val result_code: Int,
    val places: Array<PlacesDTO>
)

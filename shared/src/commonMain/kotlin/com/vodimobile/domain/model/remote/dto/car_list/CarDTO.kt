package com.vodimobile.domain.model.remote.dto.car_list

import kotlinx.serialization.Serializable

/**
    Route GET request is "car_list"
 */
@Serializable
data class CarDTO(
    val car_id: Int,
    val model: String,
    val number: String,
    val city_id: Int,
    val year: Int?,
    val photo_guid: String?
)
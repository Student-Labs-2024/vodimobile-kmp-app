package com.vodimobile.domain.model.remote.dto.car_list

import kotlinx.serialization.Serializable

@Serializable
data class CarListDTO(
    val result_code: Int,
    val cars: Array<CarDTO>
)

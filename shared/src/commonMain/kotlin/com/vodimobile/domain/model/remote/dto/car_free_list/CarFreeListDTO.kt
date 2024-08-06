package com.vodimobile.domain.model.remote.dto.car_free_list

import kotlinx.serialization.Serializable

@Serializable
data class CarFreeListDTO(
    val result_code: Int,
    val cars: Array<Int>
)

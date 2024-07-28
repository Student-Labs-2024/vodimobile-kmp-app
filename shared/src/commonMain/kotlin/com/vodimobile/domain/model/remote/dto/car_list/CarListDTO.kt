package com.vodimobile.domain.model.remote.dto.car_list

data class CarListDTO(
    val result_code: Int,
    val cars: Array<CarDTO>
)

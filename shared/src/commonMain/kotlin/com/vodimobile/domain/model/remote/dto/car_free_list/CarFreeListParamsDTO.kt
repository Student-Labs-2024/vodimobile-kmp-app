package com.vodimobile.domain.model.remote.dto.car_free_list

data class CarFreeListParamsDTO(
    val begin: String,
    val end: String,
    val includeReserves: Boolean,
    val includeIdles: Boolean,
    val cityId: Int
)

package com.vodimobile.domain.model.remote.dto.car_free_list

data class CarFreeListParamsDTO(
    val begin: Long,
    val end: Long,
    val includeReserves: Boolean,
    val includeIdles: Boolean,
    val cityId: Int
)
package com.vodimobile.domain.model.remote.dto.car_free_ate_range

data class CarFreeDateRangeParams(
    val car_id: Int,
    val begin: String,
    val end: String,
    val include_reserves: Boolean = false,
    val include_idles: Boolean = false
)
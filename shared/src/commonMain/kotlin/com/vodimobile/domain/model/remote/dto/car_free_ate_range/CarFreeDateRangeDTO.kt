package com.vodimobile.domain.model.remote.dto.car_free_ate_range

import kotlinx.serialization.Serializable

@Serializable
data class CarFreeDateRangeDTO(
    val result_code: Int,
    val car_periods: Array<CarPeriodsDTO>
)

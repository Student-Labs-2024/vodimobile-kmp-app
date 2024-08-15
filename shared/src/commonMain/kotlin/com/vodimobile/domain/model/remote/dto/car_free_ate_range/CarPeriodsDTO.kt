package com.vodimobile.domain.model.remote.dto.car_free_ate_range

import kotlinx.serialization.Serializable

@Serializable
data class CarPeriodsDTO(
    val begin: String,
    val end: String
)

package com.vodimobile.domain.model.remote.dto.tariff_list

import kotlinx.serialization.Serializable

@Serializable
data class TariffDTO(
    val min: Int,
    val max: Int,
    val cost: Float
)

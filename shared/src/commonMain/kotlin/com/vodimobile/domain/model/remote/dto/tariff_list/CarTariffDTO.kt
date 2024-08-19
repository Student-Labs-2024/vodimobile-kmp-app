package com.vodimobile.domain.model.remote.dto.tariff_list

import kotlinx.serialization.Serializable

@Serializable
data class CarTariffDTO(
    val car_id: Int,
    val deposit: Float,
    val tariffDTO: List<TariffDTO>
)

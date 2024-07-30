package com.vodimobile.domain.model.remote.dto.tariff_list

import kotlinx.serialization.Serializable

@Serializable
data class TariffListDTO(
    val result_code: Int,
    val cars: Array<CarTariffDTO>
)

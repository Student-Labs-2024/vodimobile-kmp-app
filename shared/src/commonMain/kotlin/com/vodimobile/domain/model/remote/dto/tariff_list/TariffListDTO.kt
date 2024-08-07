package com.vodimobile.domain.model.remote.dto.tariff_list

import com.vodimobile.domain.model.Tariff
import kotlinx.serialization.Serializable

@Serializable
data class TariffListDTO(
    val result_code: Int,
    val cars: Array<CarTariffDTO>
) {
    companion object {
        fun TariffListDTO.toTariff(): List<Tariff> {
            return if (this.cars[0].tariffDTO.isNotEmpty())
                this.cars[0].tariffDTO.map {
                    Tariff(
                        min = it.min,
                        max = it.max,
                        cost = it.cost
                    )
                }
            else emptyList()
        }

        fun List<TariffDTO>.toTariff(): List<Tariff> {
            return this.map {
                Tariff(
                    min = it.min,
                    max = it.max,
                    cost = it.cost
                )
            }
        }
    }
}

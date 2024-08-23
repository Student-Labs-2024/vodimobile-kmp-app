package com.vodimobile.domain.model.remote.dto.service_list

import kotlinx.serialization.Serializable

@Serializable
data class ServiceDTO(
    val result_code: Int,
    val services: Array<ServicesDTO>
)

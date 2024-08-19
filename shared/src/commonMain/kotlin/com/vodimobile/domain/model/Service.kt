package com.vodimobile.domain.model

data class Service(
    val serviceId: Int,
    val title: String,
    val cost: Float,
    val serviceType: ServiceType,
    val archive: Boolean = false
)

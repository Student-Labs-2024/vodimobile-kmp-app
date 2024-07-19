package com.vodimobile.domain.model

data class Car(
    val carId: Int,
    val model: String,
    val number: String,
    val cityId: Int,
    val year: Long?,
    val transmission: String?,
    val wheelDrive: String?,
    val tankValue: Float?,
    val deposit: Float?,
    val tariffs: List<Tariff>,
    val images: List<Int>,
)

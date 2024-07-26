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
) {
    companion object{
        fun empty() : Car {
            return Car(
                -1,
                "Hyundai Solaris",
                "",
                -1,
                null,
                "",
                "",
                null,
                null,
                listOf(
                    Tariff(min = 1, max = 5, cost = 2500f)
                ),
                emptyList()
            )
        }
    }
}

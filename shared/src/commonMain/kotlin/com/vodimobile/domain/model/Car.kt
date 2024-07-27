package com.vodimobile.domain.model

import com.vodimobile.shared.resources.SharedRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Car(
    val carId: Int,
    val model: StringResource,
    val number: String,
    val cityId: Int,
    val year: Long?,
    val transmission: StringResource,
    val wheelDrive: StringResource,
    val tankValue: StringResource,
    val deposit: Float?,
    val tariffs: List<Tariff>,
    val images: List<ImageResource>,
) {
    companion object {
        fun empty(): Car {
            val currentMoment: Instant = Clock.System.now()
            return Car(
                carId = 0,
                model = SharedRes.strings.name_0,
                number = "",
                cityId = 0,
                year = currentMoment.toEpochMilliseconds(),
                transmission = SharedRes.strings.transmission_0,
                wheelDrive = SharedRes.strings.wheel_drive_0,
                tankValue = SharedRes.strings.tank_value_0,
                deposit = 0f,
                tariffs = listOf(
                    Tariff(
                        min = 5,
                        max = 10,
                        cost = 2500f
                    )
                ),
                images = listOf(
                    SharedRes.images.hyundai_solaris_1
                )
            )
        }
    }
}

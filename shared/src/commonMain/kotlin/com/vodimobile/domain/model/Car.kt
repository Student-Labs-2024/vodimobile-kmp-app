package com.vodimobile.domain.model

import com.vodimobile.domain.model.remote.CarType
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
    val year: Int?,
    val transmission: StringResource,
    val wheelDrive: StringResource,
    val tankValue: StringResource,
    val carType: List<CarType>,
    val deposit: Float?,
    val tariffs: List<Tariff>,
    val images: List<ImageResource>,
) {
    companion object {
        fun empty(): Car {
            val currentMoment: Instant = Clock.System.now()
            return Car(
                carId = 0,
                model = SharedRes.strings.name_hyundai_solaris,
                number = "",
                cityId = 0,
                year = 2020,
                transmission = SharedRes.strings.transmission_hyundai_solaris_1,
                wheelDrive = SharedRes.strings.wheel_drive_hyundai_solaris_1,
                tankValue = SharedRes.strings.tank_value_hyundai_solaris_1,
                carType = listOf(CarType.Economy, CarType.Sedans),
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
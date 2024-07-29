package com.vodimobile.data.repository.car

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Tariff
import com.vodimobile.domain.repository.car.CarRepository
import com.vodimobile.shared.resources.SharedRes
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class CarRepositoryImpl : CarRepository {
    override fun getPopularCars(): List<Car> {

        val currentMoment: Instant = Clock.System.now()

        return listOf(
            Car(
                carId = 0,
                model = SharedRes.strings.name_hyundai_solaris,
                number = "",
                cityId = 0,
                year = currentMoment.toEpochMilliseconds(),
                transmission = SharedRes.strings.transmission_hyundai_solaris_1,
                wheelDrive = SharedRes.strings.wheel_drive_hyundai_solaris_1,
                tankValue = SharedRes.strings.tank_value_hyundai_solaris_1,
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
            ),

            Car(
                carId = 1,
                model = SharedRes.strings.name_hyundai_solaris,
                number = "",
                cityId = 0,
                year = currentMoment.toEpochMilliseconds(),
                transmission = SharedRes.strings.transmission_hyundai_solaris_1,
                wheelDrive = SharedRes.strings.wheel_drive_hyundai_solaris_1,
                tankValue = SharedRes.strings.tank_value_hyundai_solaris_1,
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
        )
    }
}
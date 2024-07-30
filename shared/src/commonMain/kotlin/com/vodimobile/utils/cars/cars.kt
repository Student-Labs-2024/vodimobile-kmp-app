package com.vodimobile.utils.cars

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Tariff
import com.vodimobile.shared.resources.SharedRes
import kotlinx.datetime.LocalDate

internal val hyundai_solaris_1 = Car(
    carId = 0,
    model = SharedRes.strings.name_hyundai_solaris,
    number = "",
    cityId = 55,
    year = LocalDate(2020, 1, 1).year,
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

internal val hyundai_creta_1 = Car(
    carId = 1,
    model = SharedRes.strings.name_hyundai_creta,
    number = "",
    cityId = 55,
    year = LocalDate(2022, 1, 1).year,
    transmission = SharedRes.strings.transmission_hyundai_creta_1,
    wheelDrive = SharedRes.strings.wheel_drive_hyundai_creta_1,
    tankValue = SharedRes.strings.tank_value_hyundai_creta_1,
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2500f
        )
    ),
    images = listOf(
        SharedRes.images.hyundai_creta_1
    )
)

internal val mercedes_e_220_1 = Car(
    carId = 2,
    model = SharedRes.strings.name_mercedes_e_220,
    number = "",
    cityId = 55,
    year = LocalDate(2019, 1, 1).year,
    transmission = SharedRes.strings.transmission_mercedes_e_220_1,
    wheelDrive = SharedRes.strings.wheel_drive_mercedes_e_220_1,
    tankValue = SharedRes.strings.tank_value_mercedes_e_220_1,
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 6000f
        )
    ),
    images = listOf(
        SharedRes.images.mercedes_e_220_1
    )
)

internal val volkswagen_polo_1 = Car(
    carId = 3,
    model = SharedRes.strings.name_volkswagen_polo,
    number = "",
    cityId = 55,
    year = LocalDate(2020, 1, 1).year,
    transmission = SharedRes.strings.transmission_volkswagen_polo_1,
    wheelDrive = SharedRes.strings.wheel_drive_volkswagen_polo_1,
    tankValue = SharedRes.strings.tank_value_volkswagen_polo_1,
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2500f
        )
    ),
    images = listOf(
        SharedRes.images.volkswagen_polo_1
    )
)

internal val datsun_on_do_1 = Car(
    carId = 4,
    model = SharedRes.strings.name_datsun_on_do,
    number = "",
    cityId = 55,
    year = LocalDate(2020, 1, 1).year,
    transmission = SharedRes.strings.transmission_datsun_on_do_1,
    wheelDrive = SharedRes.strings.wheel_drive_datsun_on_do_1,
    tankValue = SharedRes.strings.tank_value_datsun_on_do_1,
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.datsun_on_do_1
    )
)
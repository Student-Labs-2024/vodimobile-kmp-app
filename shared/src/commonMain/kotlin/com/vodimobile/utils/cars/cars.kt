package com.vodimobile.utils.cars

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Tariff
import com.vodimobile.domain.model.CarType
import com.vodimobile.shared.resources.SharedRes
import kotlinx.datetime.LocalDate

internal val hyundai_solaris_1 = Car(
    carId = 17,
    model = SharedRes.strings.name_hyundai_solaris,
    number = "",
    cityId = 2,
    year = LocalDate(2020, 1, 1).year,
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

internal val hyundai_creta_1 = Car(
    carId = 24,
    model = SharedRes.strings.name_hyundai_creta,
    number = "",
    cityId = 2,
    year = LocalDate(2022, 1, 1).year,
    transmission = SharedRes.strings.transmission_hyundai_creta_1,
    wheelDrive = SharedRes.strings.wheel_drive_hyundai_creta_1,
    tankValue = SharedRes.strings.tank_value_hyundai_creta_1,
    carType = listOf(CarType.Comfort, CarType.Jeeps),
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
    carId = 33,
    model = SharedRes.strings.name_mercedes_e_220,
    number = "",
    cityId = 2,
    year = LocalDate(2019, 1, 1).year,
    transmission = SharedRes.strings.transmission_mercedes_e_220_1,
    wheelDrive = SharedRes.strings.wheel_drive_mercedes_e_220_1,
    tankValue = SharedRes.strings.tank_value_mercedes_e_220_1,
    carType = listOf(CarType.Premium, CarType.Sedans),
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
    carId = 44,
    model = SharedRes.strings.name_volkswagen_polo,
    number = "",
    cityId = 2,
    year = LocalDate(2020, 1, 1).year,
    transmission = SharedRes.strings.transmission_volkswagen_polo_1,
    wheelDrive = SharedRes.strings.wheel_drive_volkswagen_polo_1,
    tankValue = SharedRes.strings.tank_value_volkswagen_polo_1,
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
        SharedRes.images.volkswagen_polo_1
    )
)

internal val datsun_on_do_1 = Car(
    carId = 21,
    model = SharedRes.strings.name_datsun_on_do,
    number = "",
    cityId = 2,
    year = LocalDate(2020, 1, 1).year,
    transmission = SharedRes.strings.transmission_datsun_on_do_1,
    wheelDrive = SharedRes.strings.wheel_drive_datsun_on_do_1,
    tankValue = SharedRes.strings.tank_value_datsun_on_do_1,
    carType = listOf(CarType.Economy, CarType.Sedans),
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

internal val lexus_nx200_1 = Car(
    carId = 5,
    model = SharedRes.strings.name_lexus_nx200,
    number = "",
    cityId = 2,
    year = LocalDate(2018, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_lexus_nx200_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_lexus_nx200_1,
    tankValue = SharedRes.strings.tank_value_name_lexus_nx200_1,
    carType = listOf(CarType.Premium, CarType.Jeeps),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.lexus_nx200_1
    )
)

internal val nissan_qashqai_1 = Car(
    carId = 6,
    model = SharedRes.strings.name_nissan_qashqai,
    number = "",
    cityId = 2,
    year = LocalDate(2019, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_nissan_qashqai_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_nissan_qashqai_1,
    tankValue = SharedRes.strings.tank_value_name_nissan_qashqai_1,
    carType = listOf(CarType.Comfort, CarType.Jeeps),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.nissan_qashqai_1
    )
)

internal val kia_rio_x_line_1 = Car(
    carId = 7,
    model = SharedRes.strings.name_kia_rio_x_line,
    number = "",
    cityId = 2,
    year = LocalDate(2022, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_kia_rio_x_line_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_kia_rio_x_line_1,
    tankValue = SharedRes.strings.tank_value_name_kia_rio_x_line_1,
    carType = listOf(CarType.Economy, CarType.Sedans),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.kia_rio_x_line_1
    )
)


internal val renault_duster_1 = Car(
    carId = 8,
    model = SharedRes.strings.name_renault_duster,
    number = "",
    cityId = 2,
    year = LocalDate(2019, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_renault_duster_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_renault_duster_1,
    tankValue = SharedRes.strings.tank_value_name_renault_duster_1,
    carType = listOf(CarType.Comfort, CarType.Jeeps),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.renault_duster_png_1
    )
)

internal val kia_sportage_1 = Car(
    carId = 9,
    model = SharedRes.strings.name_kia_sportage,
    number = "",
    cityId = 2,
    year = LocalDate(2019, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_kia_sportage_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_kia_sportage_1,
    tankValue = SharedRes.strings.tank_value_name_kia_sportage_1,
    carType = listOf(CarType.Comfort, CarType.Jeeps),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.kia_sportage_1
    )
)


internal val skoda_octavia_1 = Car(
    carId = 10,
    model = SharedRes.strings.name_skoda_octavia,
    number = "",
    cityId = 2,
    year = LocalDate(2022, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_skoda_octavia_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_skoda_octavia_1,
    tankValue = SharedRes.strings.tank_value_name_skoda_octavia_1,
    carType = listOf(CarType.Comfort, CarType.Sedans),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.skoda_octavia_1
    )
)

internal val omoda_s5_1 = Car(
    carId = 11,
    model = SharedRes.strings.name_omoda_s5,
    number = "",
    cityId = 2,
    year = LocalDate(2023, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_omoda_s5_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_omoda_s5_1,
    tankValue = SharedRes.strings.tank_value_name_omoda_s5_1,
    carType = listOf(CarType.Comfort, CarType.Sedans),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.omoda_s5_1
    )
)

internal val mazda_6_1 = Car(
    carId = 12,
    model = SharedRes.strings.name_mazda_6,
    number = "",
    cityId = 2,
    year = LocalDate(2021, 1, 1).year,
    transmission = SharedRes.strings.transmission_name_mazda_6_1,
    wheelDrive = SharedRes.strings.wheel_drive_name_mazda_6_1,
    tankValue = SharedRes.strings.tank_value_name_mazda_6_1,
    carType = listOf(CarType.Premium, CarType.Sedans),
    deposit = 0f,
    tariffs = listOf(
        Tariff(
            min = 5,
            max = 10,
            cost = 2000f
        )
    ),
    images = listOf(
        SharedRes.images.mazda_6_1
    )
)

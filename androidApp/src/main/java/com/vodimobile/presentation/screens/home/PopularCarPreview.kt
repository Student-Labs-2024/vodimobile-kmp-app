package com.vodimobile.presentation.screens.home

import com.vodimobile.android.R
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Tariff

class PopularCarsPreview {
    companion object {

        //Mock
        fun getPopularCarsPreview() : List<Car> {
            return listOf(
                Car(
                    carId = 0,
                    model = "Hyundai Solaris",
                    number = "",
                    cityId = 0,
                    year = System.currentTimeMillis(),
                    transmission = "АКПП-Автомат",
                    wheelDrive = "Передний",
                    tankValue = 43f,
                    deposit = 0f,
                    tariffs = listOf(
                        Tariff(
                            min = 5,
                            max = 10,
                            cost = 2500f
                        )
                    ),
                    images = listOf(
                        R.drawable.car1
                    )
                ),
                Car(
                    carId = 0,
                    model = "Hyundai Creta",
                    number = "",
                    cityId = 0,
                    year = System.currentTimeMillis(),
                    transmission = "АКПП-Автомат",
                    wheelDrive = "Передний",
                    tankValue = 43f,
                    deposit = 0f,
                    tariffs = listOf(
                        Tariff(
                            min = 10,
                            max = 20,
                            cost = 3500f
                        )
                    ),
                    images = listOf(
                        R.drawable.car2
                    )
                ),
                Car(
                    carId = 0,
                    model = "Volkswagen Polo",
                    number = "",
                    cityId = 0,
                    year = System.currentTimeMillis(),
                    transmission = "АКПП-Автомат",
                    wheelDrive = "Передний",
                    tankValue = 43f,
                    deposit = 0f,
                    tariffs = listOf(
                        Tariff(
                            min = 15,
                            max = 30,
                            cost = 2500f
                        )
                    ),
                    images = listOf(
                        R.drawable.car3
                    )
                )
            )
        }
    }
}
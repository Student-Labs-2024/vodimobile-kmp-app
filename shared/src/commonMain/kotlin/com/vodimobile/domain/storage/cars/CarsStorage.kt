package com.vodimobile.domain.storage.cars

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.use_case.cars.GetPopularCarsUseCase

class CarsStorage(
    private val getPopularCarsUseCase: GetPopularCarsUseCase
) {
    fun getPopularCars(): List<Car> = getPopularCarsUseCase()
}

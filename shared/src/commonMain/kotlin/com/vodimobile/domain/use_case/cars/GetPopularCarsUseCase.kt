package com.vodimobile.domain.use_case.cars

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.repository.car.CarRepository

class GetPopularCarsUseCase(private val carsRepository: CarRepository) {
    operator fun invoke(): List<Car> {
        return carsRepository.getPopularCars()
    }
}
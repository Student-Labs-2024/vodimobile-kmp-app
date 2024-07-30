package com.vodimobile.domain.repository.car

import com.vodimobile.domain.model.Car

interface CarRepository {
    fun getPopularCars(): List<Car>
}
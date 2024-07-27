package com.vodimobile.presentation.screens.home

import com.vodimobile.data.repository.car.CarRepositoryImpl
import com.vodimobile.domain.model.Car

class PopularCarsPreview {
    companion object {

        private val carRepository = CarRepositoryImpl()

        //Mock
        fun getPopularCarsPreview(): List<Car> {
            return carRepository.getPopularCars()
        }
    }
}
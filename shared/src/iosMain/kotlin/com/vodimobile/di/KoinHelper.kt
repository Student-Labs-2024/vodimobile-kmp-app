package com.vodimobile.di

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.storage.cars.CarsStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(carModule)
    }
}

class KoinHelper : KoinComponent {
    private val carsStorage by inject<CarsStorage>()
    fun getPopularCars(): List<Car> = carsStorage.getPopularCars()
}

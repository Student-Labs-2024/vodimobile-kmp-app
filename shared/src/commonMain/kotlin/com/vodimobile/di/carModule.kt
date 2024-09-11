package com.vodimobile.di

import com.vodimobile.data.repository.car.CarRepositoryImpl
import com.vodimobile.domain.repository.car.CarRepository
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.use_case.cars.GetPopularCarsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val carModule = module {
    singleOf(::CarRepositoryImpl).bind<CarRepository>()
    singleOf(::GetPopularCarsUseCase)
    singleOf(::CarsStorage)
}

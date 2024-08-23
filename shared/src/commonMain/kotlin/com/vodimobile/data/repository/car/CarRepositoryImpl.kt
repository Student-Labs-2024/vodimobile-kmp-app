package com.vodimobile.data.repository.car

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.repository.car.CarRepository
import com.vodimobile.utils.cars.datsun_on_do_1
import com.vodimobile.utils.cars.hyundai_creta_1
import com.vodimobile.utils.cars.hyundai_solaris_1
import com.vodimobile.utils.cars.mercedes_e_220_1
import com.vodimobile.utils.cars.volkswagen_polo_1

class CarRepositoryImpl : CarRepository {
    override fun getPopularCars(): List<Car> {

        return listOf(
            hyundai_solaris_1,
            hyundai_creta_1,
            mercedes_e_220_1,
            volkswagen_polo_1,
            datsun_on_do_1
        )
    }
}

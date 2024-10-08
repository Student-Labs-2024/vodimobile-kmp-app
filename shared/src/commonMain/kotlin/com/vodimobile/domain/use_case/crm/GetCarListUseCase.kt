package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.car_list.CarDTO
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.utils.cars.carsMap
import com.vodimobile.utils.cars.hyundai_solaris_1
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetCarListUseCase(
    private val crmRepository: CrmRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ): CrmEither<List<Car>, HttpStatusCode> {
        val crmEither: CrmEither<CarListDTO, HttpStatusCode> =
            crmRepository.getCarList(accessToken, refreshToken)

        return when (crmEither) {
            is CrmEither.CrmData -> {
                val carsDto: List<CarDTO> = crmEither.data.cars.toList()

                val carList = carsDto.map { dto ->
                    val carFromLocal = carsMap[dto.car_id] ?: hyundai_solaris_1
                    Car(
                        carId = dto.car_id,
                        cityId = dto.city_id,
                        number = dto.number,
                        model = carFromLocal.model,
                        year = dto.year,
                        transmission = carFromLocal.transmission,
                        tankValue = carFromLocal.tankValue,
                        wheelDrive = carFromLocal.wheelDrive,
                        deposit = carFromLocal.deposit,
                        tariffs = carFromLocal.tariffs,
                        images = carFromLocal.images,
                        carType = carFromLocal.carType
                    )
                }
                CrmEither.CrmData(data = carList)
            }

            is CrmEither.CrmError -> {
                CrmEither.CrmError(status = crmEither.status)
            }

            CrmEither.CrmLoading -> {
                CrmEither.CrmLoading
            }
        }
    }

    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        carIds: Array<Int>
    ): CrmEither<List<Car>, HttpStatusCode> {

        val crmEither: CrmEither<CarListDTO, HttpStatusCode> =
            crmRepository.getCarList(accessToken, refreshToken)

        return when (crmEither) {
            is CrmEither.CrmData -> {
                val carsDto: List<CarDTO> = crmEither.data.cars.toList()

                val carList = carsDto
                    .map { dto ->
                        val carFromLocal = carsMap[dto.car_id] ?: hyundai_solaris_1
                        Car(
                            carId = dto.car_id,
                            cityId = dto.city_id,
                            number = dto.number,
                            model = carFromLocal.model,
                            year = dto.year,
                            transmission = carFromLocal.transmission,
                            tankValue = carFromLocal.tankValue,
                            wheelDrive = carFromLocal.wheelDrive,
                            deposit = carFromLocal.deposit,
                            tariffs = carFromLocal.tariffs,
                            images = carFromLocal.images,
                            carType = carFromLocal.carType
                        )
                    }
                    .filter { it.carId in carIds }

                CrmEither.CrmData(data = carList)
            }

            is CrmEither.CrmError -> {
                CrmEither.CrmError(status = crmEither.status)
            }

            CrmEither.CrmLoading -> {
                CrmEither.CrmLoading
            }
        }
    }
}

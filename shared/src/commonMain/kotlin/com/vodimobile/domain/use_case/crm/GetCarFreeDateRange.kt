package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeDTO
import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.utils.date_formats.parseToCrmDateTime
import com.vodimobile.utils.date_formats.parseToLong
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class GetCarFreeDateRange(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        carId: Int,
        begin: Long,
        end: Long
    ): List<Pair<Long, Long>> {
        val carFreeDateRangeEither: CrmEither<CarFreeDateRangeDTO, HttpStatusCode> =
            crmRepository.getCarFreeDateRange(
                accessToken,
                refreshToken,
                CarFreeDateRangeParams(
                    car_id = carId,
                    begin = begin.parseToCrmDateTime(),
                    end = end.parseToCrmDateTime()
                )
            )

        when (carFreeDateRangeEither) {
            is CrmEither.CrmData -> {
                return carFreeDateRangeEither.data.car_periods.map {
                    Pair(
                        first = it.begin.parseToLong(),
                        second = it.end.parseToLong()
                    )
                }
            }

            is CrmEither.CrmError -> {
                return emptyList()
            }

            CrmEither.CrmLoading -> {
                return emptyList()
            }
        }
    }
}

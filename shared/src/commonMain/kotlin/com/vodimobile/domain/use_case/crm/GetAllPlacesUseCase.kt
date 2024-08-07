package com.vodimobile.domain.use_case.crm

import com.vodimobile.domain.model.Place
import com.vodimobile.domain.model.Place.Companion.toPlace
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import io.ktor.http.HttpStatusCode

class GetAllPlacesUseCase(private val crmRepository: CrmRepository) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ) : CrmEither<List<Place>, HttpStatusCode> {

        return when(val crm = crmRepository.getAllPlaces(accessToken, refreshToken)) {
            is CrmEither.CrmData -> {
                val list = crm.data.places.map {
                    it.toPlace()
                }
                CrmEither.CrmData(data = list)
            }
            is CrmEither.CrmError -> {
                CrmEither.CrmError(status = crm.status)
            }
            CrmEither.CrmLoading -> {
                CrmEither.CrmLoading
            }
        }
    }
}
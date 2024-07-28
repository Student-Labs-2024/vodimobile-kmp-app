package com.vodimobile.data.repository.crm

import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.domain.model.crm.CrmServerData.Companion.buildUrl
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.utils.local_properties.getCrmServerDataFromLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.isSuccess

class CrmRepositoryImpl(private val client: HttpClient) : CrmRepository {

    private val crmServerData: CrmServerData = getCrmServerDataFromLocalProperties()

    override suspend fun getCarList(): CrmEither<CarListDTO, HttpStatusCode> {
        val httpResponse: HttpResponse = client.get(
            block = {
                url(url = Url(crmServerData.buildUrl("car_list")))
            }
        )

        return if (httpResponse.status.isSuccess()) {
            CrmEither(data = httpResponse.body(), status = httpResponse.status)
        } else {
            CrmEither(data = null, status = httpResponse.status)
        }
    }
}
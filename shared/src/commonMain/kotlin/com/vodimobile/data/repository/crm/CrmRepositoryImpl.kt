package com.vodimobile.data.repository.crm

import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.domain.model.crm.CrmServerData.Companion.buildUrl
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.utils.local_properties.getCrmServerDataFromLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class CrmRepositoryImpl(private val client: HttpClient) : CrmRepository {

    private val crmServerData: CrmServerData = getCrmServerDataFromLocalProperties()

    override suspend fun getCarList(): CrmEither<CarListDTO, HttpStatusCode> {
        val httpResponse: HttpResponse = client.get(
            block = {
                url(url = Url(crmServerData.buildUrl(CrmRouting.Cars.ALL_CARS)))
            }
        )

        val carListDTO: CarListDTO = httpResponse.body()

        return if (httpResponse.status.isSuccess()) {
            CrmEither(data = carListDTO, status = httpResponse.status)
        } else {
            CrmEither(data = null, status = httpResponse.status)
        }
    }

    override suspend fun getTariffList(carId: Int): CrmEither<TariffListDTO, HttpStatusCode> {
        val httpResponse: HttpResponse = client.get(
            block = {
                url(url = Url(crmServerData.buildUrl(CrmRouting.Tariff.TARIFFS_BY_CAR_ID)))
                parameter(
                    key = CrmRouting.Tariff.PARAM.TARIFF_BY_CAR_ID_PARAM_CAR_ID,
                    value = carId
                )
            }
        )

        return if (httpResponse.status.isSuccess()) {
            val tariffListDTO: TariffListDTO = httpResponse.body()
            CrmEither(data = tariffListDTO, status = httpResponse.status)
        } else {
            CrmEither(data = null, status = httpResponse.status)
        }
    }

    override suspend fun postNewUser(userRequest: UserRequest): CrmEither<UserResponse, HttpStatusCode> {
        val response: HttpResponse = client.post(block = {
            url(url = Url(urlString = "$crmServerData/${CrmRouting.UserAuth.USER_AUTH}"))
            contentType(ContentType.Application.Json)
            setBody(userRequest)
        })

        return if (response.status.isSuccess()) {
            val userResponse: UserResponse = response.body()
            CrmEither(data = userResponse, status = response.status)
        } else {
            CrmEither(data = null, status = response.status)
        }
    }
}
package com.vodimobile.data.repository.crm

import com.vodimobile.domain.client.provideKtorClient
import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.domain.model.crm.CrmServerData.Companion.buildUrl
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import com.vodimobile.utils.local_properties.getCrmServerDataFromLocalProperties
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CrmRepositoryImpl : CrmRepository {

    private val crmServerData: CrmServerData = getCrmServerDataFromLocalProperties()
    private val client: HttpClient = provideKtorClient()

    override suspend fun getCarList(bearerTokens: BearerTokens): CrmEither<CarListDTO, HttpStatusCode> {
        client.config {
            install(Auth) {
                bearer {
                    loadTokens {
                        bearerTokens
                    }
                }
            }
        }
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
//        val encodeToString: String = Json.encodeToString(userRequest)

        val tmp = """
            {
                "UserName": "USER_FOR_SITE",
                "PasswordHash" : "f7f8e967f2756082ada759e8e189e772e00a1deb13583e9beee02ad9a5420fda5ce2069e942cdc22a8cae4ecb90ace005737c6c96dd655330f0f1ae6a0ffd8fc",
                "LongToken" : true
            }
        """.trimIndent()
        val response: HttpResponse = client.post(
            block = {
                url(url = Url(urlString = "http://$crmServerData/${CrmRouting.UserAuth.USER_AUTH}"))
                contentType(ContentType.Application.Json)
                setBody(tmp)
            })

        return if (response.status.isSuccess()) {
            val userResponse: UserResponse = response.body()
            CrmEither(data = userResponse, status = response.status)
        } else {
            CrmEither(data = null, status = response.status)
        }
    }
}
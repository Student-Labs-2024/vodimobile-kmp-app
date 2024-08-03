package com.vodimobile.data.repository.crm

import com.vodimobile.domain.client.provideKtorClient
import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.domain.model.crm.CrmServerData.Companion.buildUrl
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostDTO
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListDTO
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.place_list.PlaceDTO
import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.model.remote.dto.service_list.ServiceDTO
import com.vodimobile.domain.model.remote.dto.service_list.ServicesDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import com.vodimobile.utils.date_formats.parseToCrmDate
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
import io.ktor.http.parameters
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CrmRepositoryImpl : CrmRepository {

    private val crmServerData: CrmServerData = getCrmServerDataFromLocalProperties()
    private val client: HttpClient = provideKtorClient()

    override suspend fun getCarList(
        accessToken: String,
        refreshToken: String
    ): CrmEither<CarListDTO, HttpStatusCode> {
        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
                    block = {
                        url(url = Url(crmServerData.buildUrl(CrmRouting.Cars.ALL_CARS)))
                    }
                )

        return if (httpResponse.status.isSuccess()) {
            CrmEither.CrmData(data = httpResponse.body())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun getTariffList(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode> {

        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
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
            CrmEither.CrmData(data = tariffListDTO)
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun postNewUser(userRequest: UserRequest): CrmEither<UserResponse, HttpStatusCode> {
//        val encodeToString: String = Json.encodeToString(userRequest)

        val tmp = """
            {
                "UserName": ${SharedBuildkonfig.crm_login},
                "PasswordHash" : ${SharedBuildkonfig.crm_password_hash}",
                "LongToken" : false
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
            CrmEither.CrmData(data = userResponse)
        } else {
            CrmEither.CrmError(status = response.status)
        }
    }

    override suspend fun getAllPlaces(
        accessToken: String,
        refreshToken: String
    ): CrmEither<PlaceDTO, HttpStatusCode> {
        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
                    block = {
                        url(url = Url(crmServerData.buildUrl(CrmRouting.Places.ALL_PLACES)))
                    }
                )

        return if (httpResponse.status.isSuccess()) {
            CrmEither.CrmData(data = httpResponse.body())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun getFreeCars(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ): CrmEither<CarFreeListDTO, HttpStatusCode> {
        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
                    block = {
                        url(url = Url(crmServerData.buildUrl(CrmRouting.CarFreeList.CAR_FREE_LIST)))
                        parameters {
                            parameter(
                                CrmRouting.CarFreeList.PARAM.BEGIN,
                                carFreeListParamsDTO.begin.parseToCrmDate()
                            )
                            parameter(
                                CrmRouting.CarFreeList.PARAM.END,
                                carFreeListParamsDTO.end.parseToCrmDate()
                            )
                            parameter(
                                CrmRouting.CarFreeList.PARAM.CITY_ID,
                                carFreeListParamsDTO.cityId
                            )
                            parameter(
                                CrmRouting.CarFreeList.PARAM.INCLUDE_IDLES,
                                carFreeListParamsDTO.includeIdles
                            )
                            parameter(
                                CrmRouting.CarFreeList.PARAM.INCLUDE_RESERVES,
                                carFreeListParamsDTO.includeReserves
                            )
                        }
                    }
                )

        return if (httpResponse.status.isSuccess()) {
            CrmEither.CrmData(data = httpResponse.body())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun getAllServices(
        accessToken: String,
        refreshToken: String
    ): CrmEither<List<ServicesDTO>, HttpStatusCode> {

        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
                    block = {
                        url(url = Url(crmServerData.buildUrl(CrmRouting.ServiceList.SERVICE_LIST)))
                    }
                )

        return if (httpResponse.status.isSuccess()) {
            val serviceDTO: ServiceDTO = httpResponse.body()
            CrmEither.CrmData(data = serviceDTO.services.toList())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): CrmEither<UserResponse, HttpStatusCode> {
        val httpResponse: HttpResponse = client.post(block = {
            url(url = Url(urlString = "http://$crmServerData/${CrmRouting.RefreshToken.REFRESH_TOKEN}"))
            setBody(
                Json.encodeToString(refreshTokenRequest)
            )
        })

        return if (httpResponse.status.isSuccess()) {
            CrmEither.CrmData(data = httpResponse.body())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    override suspend fun getBidCost(
        accessToken: String,
        refreshToken: String,
        bidCostParams: BidCostParams
    ): CrmEither<BidCostDTO, HttpStatusCode> {
        val httpResponse: HttpResponse =
            authConfig(accessToken, refreshToken)
                .get(
                    block = {
                        url(url = Url(crmServerData.buildUrl(CrmRouting.BidCost.BID_COST)))
                        parameters {
                            parameter(CrmRouting.BidCost.PARAM.BEGIN, bidCostParams.begin)
                            parameter(CrmRouting.BidCost.PARAM.END, bidCostParams.end)
                            parameter(CrmRouting.BidCost.PARAM.CAR_ID, bidCostParams.car_id)
                            parameter(
                                CrmRouting.BidCost.PARAM.BEGIN_PLACE_ID,
                                bidCostParams.begin_place_id
                            )
                            parameter(
                                CrmRouting.BidCost.PARAM.END_PLACE_ID,
                                bidCostParams.end_place_id
                            )
                        }
                    }
                )

        return if (httpResponse.status.isSuccess()) {
            CrmEither.CrmData(data = httpResponse.body())
        } else {
            CrmEither.CrmError(status = httpResponse.status)
        }
    }

    private fun authConfig(accessToken: String, refreshToken: String): HttpClient {
        return client.config {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
                    }
                    refreshTokens {
                        BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
                    }
                }
            }
        }
    }
}
package com.vodimobile.domain.storage.crm

import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.use_case.crm.CreateBidUseCase
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarFreeDateRange
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import io.ktor.http.HttpStatusCode

class CrmStorage(
    private val getCarListUseCase: GetCarListUseCase,
    private val getTariffListUseCase: GetTariffListUseCase,
    private val postNewUserUseCase: PostNewUserUseCase,
    private val getAllPlacesUseCase: GetAllPlacesUseCase,
    private val getFreeCarsUseCaSE: GetFreeCarsUseCaSE,
    private val getServiceListUseCase: GetServiceListUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getBidCostUseCase: GetBidCostUseCase,
    private val getCarFreeDateRange: GetCarFreeDateRange,
    private val createBidUseCase: CreateBidUseCase
) {
    suspend fun getCarList(
        accessToken: String,
        refreshToken: String
    ): CrmEither<List<Car>, HttpStatusCode> {
        return getCarListUseCase(accessToken = accessToken, refreshToken = refreshToken)
    }

    suspend fun getCarList(
        accessToken: String,
        refreshToken: String,
        carIds: Array<Int>
    ): CrmEither<List<Car>, HttpStatusCode> {
        return getCarListUseCase(
            accessToken = accessToken,
            refreshToken = refreshToken,
            carIds = carIds
        )
    }

    suspend fun getTariffByCar(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode> {
        return getTariffListUseCase(
            carId = carId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    suspend fun authUser(): CrmEither<UserResponse, HttpStatusCode> {
        return postNewUserUseCase()
    }

    suspend fun getPlaces(
        accessToken: String,
        refreshToken: String
    ) = getAllPlacesUseCase.invoke(
        accessToken,
        refreshToken
    )

    suspend fun getFreeCars(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ) = getFreeCarsUseCaSE.invoke(accessToken, refreshToken, carFreeListParamsDTO)

    suspend fun getServices(accessToken: String, refreshToken: String) =
        getServiceListUseCase.invoke(accessToken, refreshToken)

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest) =
        refreshTokenUseCase.invoke(refreshTokenRequest)

    suspend fun getBidCost(
        accessToken: String,
        refreshToken: String,
        bidCostParams: BidCostParams
    ) = getBidCostUseCase.invoke(accessToken, refreshToken, bidCostParams)

    suspend fun getCarFreeDateRange(
        accessToken: String,
        refreshToken: String,
        carId: Int,
        begin: Long,
        end: Long
    ) = getCarFreeDateRange.invoke(
        accessToken = accessToken,
        refreshToken = refreshToken,
        carId = carId,
        begin = begin,
        end = end
    )

    suspend fun createBid(
        accessToken: String,
        refreshToken: String,
        bidCreateParams: BidCreateParams
    ) = createBidUseCase(accessToken, refreshToken, bidCreateParams)
}

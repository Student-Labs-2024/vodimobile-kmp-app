package com.vodimobile.domain.repository.crm

import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostDTO
import com.vodimobile.domain.model.remote.dto.bid_cost.BidCostParams
import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeDTO
import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeParams
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListDTO
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.dto.car_list.CarListDTO
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateDTO
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.dto.place_list.PlaceDTO
import com.vodimobile.domain.model.remote.dto.refresh_token.RefreshTokenRequest
import com.vodimobile.domain.model.remote.dto.service_list.ServicesDTO
import com.vodimobile.domain.model.remote.dto.tariff_list.TariffListDTO
import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.http.HttpStatusCode

interface CrmRepository {
    suspend fun getCarList(
        accessToken: String,
        refreshToken: String
    ): CrmEither<CarListDTO, HttpStatusCode>

    suspend fun getTariffList(
        carId: Int,
        accessToken: String,
        refreshToken: String
    ): CrmEither<TariffListDTO, HttpStatusCode>

    suspend fun postNewUser(): CrmEither<UserResponse, HttpStatusCode>

    suspend fun getAllPlaces(
        accessToken: String,
        refreshToken: String
    ): CrmEither<PlaceDTO, HttpStatusCode>

    suspend fun getFreeCars(
        accessToken: String,
        refreshToken: String,
        carFreeListParamsDTO: CarFreeListParamsDTO
    ): CrmEither<CarFreeListDTO, HttpStatusCode>

    suspend fun getAllServices(
        accessToken: String,
        refreshToken: String
    ): CrmEither<List<ServicesDTO>, HttpStatusCode>

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): CrmEither<UserResponse, HttpStatusCode>

    suspend fun getBidCost(
        accessToken: String,
        refreshToken: String,
        bidCostParams: BidCostParams
    ): CrmEither<BidCostDTO, HttpStatusCode>

    suspend fun getCarFreeDateRange(
        accessToken: String,
        refreshToken: String,
        carFreeDateRangeParams: CarFreeDateRangeParams
    ): CrmEither<CarFreeDateRangeDTO, HttpStatusCode>

    suspend fun createBid(
        accessToken: String,
        refreshToken: String,
        bidCreateParams: BidCreateParams
    ): CrmEither<BidCreateDTO, HttpStatusCode>
}
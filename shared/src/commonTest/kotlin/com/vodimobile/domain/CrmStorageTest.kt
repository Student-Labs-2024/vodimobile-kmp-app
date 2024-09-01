package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRouting
import com.vodimobile.di.crmModule
import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.domain.model.crm.CrmServerData.Companion.buildUrl
import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeParams
import com.vodimobile.domain.model.remote.dto.create_bid.BidCreateParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
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
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.http.isSuccess
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class CrmStorageTest {

    @Test
    fun getCarsRequestUsingCrmStorageTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )

            val response = crmStorage.getCarList(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )
            assertResponse(response)
        }
    }

    @Test
    fun getPlacesRequestUsingCrmStorageTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )

            val response = crmStorage.getPlaces(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )
            assertResponse(response)
        }
    }

    @Test
    fun getFreeCarDateRangeUsingCrmStorageTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )

            val response = crmStorage.getCarFreeDateRange(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token,
                begin = "2024-10-01 10:00",
                end = "2024-10-03 10:00",
                carId = 17,
            )

            assertTrue(actual = response.isNotEmpty())
        }
    }

    private fun <D, E> assertResponse(response: CrmEither<D, E>) {
        when (response) {
            is CrmEither.CrmData -> {
                assertTrue(true, "Data is loaded \n${response.data}")
            }

            is CrmEither.CrmError -> {
                assertTrue(false, "Error: ${response.status}")
            }

            CrmEither.CrmLoading -> {
                assertTrue(false, "Data is loading")
            }
        }
    }
}

package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.domain.model.remote.dto.car_free_ate_range.CarFreeDateRangeParams
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
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
    fun carsRequestTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl()),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = CrmRepositoryImpl())
            )

            val response = crmStorage.getCarList(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )
            assertResponse(response)
        }
    }

    @Test
    fun getPlacesRequestTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val response = crmRepository.getAllPlaces(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )
            assertResponse(response)
        }
    }

    @Test
    fun getFreeCarDateRangeTest() {
        runBlocking {
            val crmRepository = CrmRepositoryImpl()

            val response = crmRepository.getCarFreeDateRange(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token,
                carFreeDateRangeParams = CarFreeDateRangeParams(
                    car_id = 17,
                    begin = "2024-10-01 10:00",
                    end = "2024-10-03 10:00",
                    include_idles = true,
                    include_reserves = true
                )
            )

            assertResponse(response = response)
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
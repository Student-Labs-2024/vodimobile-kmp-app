package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
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
                getCarListUseCase = GetCarListUseCase(crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository)
            )

            val response = crmStorage.getCarList(
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )

            assertTrue(response.status.isSuccess())
        }
    }
}
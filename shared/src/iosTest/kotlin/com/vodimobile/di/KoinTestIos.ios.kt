package com.vodimobile.di

import com.vodimobile.domain.model.User
import kotlinx.coroutines.runBlocking
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.Test

class KoinTestIos : KoinTest {

    private val koinHelper: KoinHelper = KoinHelper()

    @BeforeTest
    fun setup() {
        initKoin()

        runBlocking {
            koinHelper.userDataStoreStorage.edit(
                user = User.empty()
            )
        }
    }

    @AfterTest
    fun shutdown() {
        stopKoin()
    }

    @Test
    fun checkIosModules() = runBlocking {
        var user: User = User.empty()
        koinHelper.userDataStoreStorage.getUser().collect {
            user = it
        }

        assertEquals(expected = User.empty(), actual = user)

    }
}
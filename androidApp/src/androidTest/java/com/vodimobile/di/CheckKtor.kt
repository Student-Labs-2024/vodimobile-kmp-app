package com.vodimobile.di

import com.vodimobile.domain.storage.crm.CrmStorage
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify

class CheckKtor : KoinTest {

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            modules(crmModule)
        }
    }

    @After
    fun shutdown() {
        stopKoin()
    }



}
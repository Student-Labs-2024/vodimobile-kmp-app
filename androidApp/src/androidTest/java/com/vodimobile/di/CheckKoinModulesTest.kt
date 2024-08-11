package com.vodimobile.di

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckKoinModulesTest : KoinTest {

    @Before
    fun start() {
        stopKoin()
        startKoin {
            modules(viewModelModule, validatorModule, repositoryModule, androidModule, crmModule, supabaseModule)
        }
    }

    @After
    fun shutDown() {
        unloadKoinModules(listOf(viewModelModule, validatorModule, repositoryModule, crmModule, supabaseModule))
        stopKoin()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkRepositoryModule() {
        repositoryModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkViewModelModule() {
        viewModelModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkValidatorModule() {
        validatorModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAndroidModule() {
        androidModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKtorModule() {
        crmModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkSupabaseModule() {
        supabaseModule.verify()
    }
}
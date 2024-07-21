package com.vodimobile.di

import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.verify.verify
import kotlin.test.assertNotNull

class CheckKoinModulesTest : KoinTest {

    @Before
    fun start() {
        stopKoin()
        startKoin {
            modules(viewModelModule, validatorModule, repositoryModule)
        }
    }

    @After
    fun shutDown() {
        unloadKoinModules(listOf(viewModelModule, validatorModule, repositoryModule))
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
}
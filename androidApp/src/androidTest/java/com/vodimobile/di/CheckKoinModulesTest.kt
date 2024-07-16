package com.vodimobile.di

import com.vodimobile.presentation.screens.startscreen.StartScreenViewModel
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
            modules(viewModelModule, validatorModule)
        }
    }

    @After
    fun shutDown() {
        unloadKoinModules(listOf(viewModelModule, validatorModule))
        stopKoin()
    }

    @Test
    fun checkViewModelModule() {
        val startScreenViewModel: StartScreenViewModel by inject<StartScreenViewModel>()
        assertNotNull(startScreenViewModel)
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkValidatorModule() {
        validatorModule.verify()
    }
}
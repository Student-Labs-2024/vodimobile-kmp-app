package com.vodimobile

import com.vodimobile.di.KoinHelper
import com.vodimobile.di.initKoin
import com.vodimobile.domain.model.User
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IosGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("iOS"), "Check iOS is mentioned")
    }

    @Test
    fun test() {
        val koinHelper = KoinHelper()
        initKoin()
        runBlocking {
            val actualUser = koinHelper.getUser(password = "10101010K+", phone = "+79029994148")
            assertEquals(expected = User.empty(), actual = actualUser)
        }
    }

    @Test
    fun test2() {
        val koinHelper = KoinHelper()
        initKoin()
        runBlocking {
            val actualUser = koinHelper.supabaseStorage.getUser(password = "10101010K+", phone = "+79029994148")
            assertEquals(expected = User.empty(), actual = actualUser)
        }
    }
}
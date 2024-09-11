package com.vodimobile.domain

import com.vodimobile.data.repository.hash.HashRepositoryImpl
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HashRepositoryTest {

    @Test
    fun hashTextTest() {
        runBlocking {
            val test = "test"
            val hashRepository = HashRepositoryImpl()
            val hashText: ByteArray = hashRepository.hash(text = test)
            val expected = byteArrayOf(
                -18,
                38,
                -80,
                -35,
                74,
                -9,
                -25,
                73,
                -86,
                26,
                -114,
                -29,
                -63,
                10,
                -23,
                -110,
                63,
                97,
                -119,
                -128,
                119,
                46,
                71,
                63,
                -120,
                25,
                -91,
                -44,
                -108,
                14,
                13,
                -78,
                122,
                -63,
                -123,
                -8,
                -96,
                -31,
                -43,
                -8,
                79,
                -120,
                -68,
                -120,
                127,
                -42,
                123,
                20,
                55,
                50,
                -61,
                4,
                -52,
                95,
                -87,
                -83,
                -114,
                111,
                87,
                -11,
                0,
                40,
                -88,
                -1
            )
            assertEquals(expected = expected.size, actual = hashText.size)
        }
    }

    @Test
    fun verifyTextHashTest() {
        runBlocking {
            val test = "test"
            val hashRepository = HashRepositoryImpl()
            val hashText: ByteArray = hashRepository.hash(text = test)

            assertTrue(actual = hashRepository.verify(text = test, byteArray = hashText))
        }
    }

    @Test
    fun verifyTextHashEmptyTest() {
        runBlocking {
            val test = ""
            val hashRepository = HashRepositoryImpl()
            val hashText: ByteArray = hashRepository.hash(text = test)

            assertTrue(actual = !hashRepository.verify(text = test, byteArray = hashText))
        }
    }

    @Test
    fun verifyTextHashNotEqualsTest() {
        runBlocking {
            val test = "hello world"
            val hashRepository = HashRepositoryImpl()
            val hashText: ByteArray = hashRepository.hash(text = "test")

            assertFalse(actual = hashRepository.verify(text = test, byteArray = hashText))
        }
    }
}
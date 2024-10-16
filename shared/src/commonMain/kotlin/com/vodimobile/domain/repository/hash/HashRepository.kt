package com.vodimobile.domain.repository.hash

interface HashRepository {
    suspend fun hash(text: String): ByteArray
    suspend fun verify(text: String, byteArray: ByteArray): Boolean

    fun generateKey() : ByteArray
    fun encrypt(key: ByteArray, plainText: String): ByteArray
    fun decrypt(key: ByteArray, cipherText: ByteArray): String
}
package com.vodimobile.data.cryptography

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Cryptography() {
    val algorithm: String

    fun generateKey() : ByteArray
    fun encrypt(key: ByteArray, plainText: String): ByteArray
    fun decrypt(key: ByteArray, cipherText: ByteArray): String
}

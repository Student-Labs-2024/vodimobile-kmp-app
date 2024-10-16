package com.vodimobile.data.repository.hash

import com.vodimobile.data.cryptography.Cryptography
import com.vodimobile.domain.repository.hash.HashRepository
import com.vodimobile.utils.hash.toByteArray
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.digest.SHA512

class HashRepositoryImpl : HashRepository {

    private val cryptography = Cryptography()

    override suspend fun hash(text: String): ByteArray {
        return hashText(text = text)
    }

    override suspend fun verify(text: String, byteArray: ByteArray): Boolean {
        if (text.isNotEmpty()) {
            val textByteArray: ByteArray = hashText(text = text)
            return textByteArray.all { it in byteArray }
        } else {
            return false
        }
    }

    override fun generateKey(): ByteArray {
        return cryptography.generateKey()
    }

    override fun encrypt(key: ByteArray, plainText: String): ByteArray {
        return cryptography.encrypt(key, plainText)
    }

    override fun decrypt(key: ByteArray, cipherText: ByteArray): String {
        return cryptography.decrypt(key, cipherText)
    }

    private suspend fun hashText(text: String): ByteArray {
        val hash: ByteArray = CryptographyProvider.Default
            .get(SHA512)
            .hasher()
            .hash(text.toByteArray())
        return hash
    }
}
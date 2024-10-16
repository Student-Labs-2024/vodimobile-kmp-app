package com.vodimobile.data.cryptography

import android.annotation.SuppressLint
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Cryptography {

    actual val algorithm: String = "AES"

    actual fun generateKey(): ByteArray {
        val random = SecureRandom()
        val keyBytes = ByteArray(16)
        random.nextBytes(keyBytes)
        return keyBytes
    }

    // Шифрование
    @SuppressLint("GetInstance")
    actual fun encrypt(key: ByteArray, plainText: String): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key, algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return encryptedBytes
    }

    // Дешифрование
    @SuppressLint("GetInstance")
    actual fun decrypt(key: ByteArray, cipherText: ByteArray): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key, algorithm)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decodedBytes = cipher.doFinal(cipherText)
        return String(decodedBytes, Charsets.UTF_8)
    }
}
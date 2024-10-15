package com.vodimobile.data.cryptography

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Cryptography {

    actual val algorithm: String = "AES"

    actual fun generateKey(): ByteArray {
        val keyGenerator = javax.crypto.KeyGenerator.getInstance(algorithm)
        keyGenerator.init(128)
        return keyGenerator.generateKey().encoded
    }

    // Шифрование
    @SuppressLint("GetInstance")
    actual fun encrypt(key: ByteArray, plainText: String): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key, algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(plainText.toByteArray())
    }

    // Дешифрование
    @SuppressLint("GetInstance")
    actual fun decrypt(key: ByteArray, cipherText: ByteArray): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key, algorithm)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return String(cipher.doFinal(cipherText))
    }
}
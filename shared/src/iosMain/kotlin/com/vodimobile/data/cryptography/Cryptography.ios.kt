package com.vodimobile.data.cryptography

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.CommonCrypto.*

import platform.Security.CCCrypt
import platform.Security.kCCDecrypt
import platform.Security.kCCEncrypt
import platform.Security.kCCOptionPKCS7Padding

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Cryptography {
    actual val algorithm: String = "AES"

    actual fun generateKey(): ByteArray {
        val keySize = 128 / 8
        val key = ByteArray(keySize)
        return key
    }

    actual fun encrypt(key: ByteArray, plainText: String): ByteArray {
        val keyData = NSData(key, key.size)
        val plainTextData = plainText.encodeToByteArray()
        val cipherTextData = encryptData(keyData, plainTextData)
        return cipherTextData.toByteArray()
    }

    actual fun decrypt(key: ByteArray, cipherText: ByteArray): String {
        val keyData = NSData(key, key.size)
        val cipherTextData = NSData(cipherText, cipherText.size)
        val plainTextData = decryptData(keyData, cipherTextData)
        return plainTextData.toByteArray().concatToString()
    }

    private fun encryptData(keyData: NSData, plainTextData: ByteArray): NSData {
        return performCCOperation(kCCEncrypt, keyData, plainTextData)
    }

    private fun decryptData(keyData: NSData, cipherTextData: NSData): NSData {
        return performCCOperation(kCCDecrypt, keyData, cipherTextData)
    }

    private fun performCCOperation(operation: Int, keyData: NSData, data: ByteArray): NSData {
        val algorithm = kCCAlgorithmAES
        val options = kCCOptionPKCS7Padding
        val blockSize = 16

        var numBytesEncrypted: Int = 0
        val cipherText = NSMutableData(data.size + blockSize)!!
        val result = CCCrypt(
            operation,
            algorithm,
            options,
            keyData.bytes,
            keyData.length,
            null,
            data,
            data.size,
            cipherText.mutableBytes,
            cipherText.length,
            numBytesEncrypted
        )

        if (result != 0) {
            // Handle encryption/decryption error
            throw Exception("CCCrypt failed")
        }
        return cipherText
    }
}
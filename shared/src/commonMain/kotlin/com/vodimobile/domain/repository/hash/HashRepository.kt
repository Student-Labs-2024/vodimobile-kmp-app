package com.vodimobile.domain.repository.hash

interface HashRepository {
    suspend fun hash(text: String) : ByteArray
    suspend fun verify(text: String, byteArray: ByteArray) : Boolean
}
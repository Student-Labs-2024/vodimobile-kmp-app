package com.vodimobile.domain.repository

import com.vodimobile.domain.model.HelloWorld

interface HelloWorldRepository {
    suspend fun getHelloWorld(): HelloWorld
}
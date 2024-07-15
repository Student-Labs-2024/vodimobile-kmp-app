package com.vodimobile.data.repository

import com.vodimobile.domain.model.HelloWorld
import com.vodimobile.domain.repository.HelloWorldRepository

class HelloWorldRepositoryImpl : HelloWorldRepository {
    override suspend fun getHelloWorld(): HelloWorld {
        val helloWorld = HelloWorld(hello = "Hello", word = "world!")
        return helloWorld
    }
}
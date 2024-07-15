package com.vodimobile.domain.usecase

import com.vodimobile.domain.model.HelloWorld
import com.vodimobile.domain.repository.HelloWorldRepository
import kotlinx.coroutines.delay

class GetHelloWorldUseCase (private val helloWorldRepository: HelloWorldRepository) {
    suspend fun execute() : HelloWorld {
        delay(5000L)
        return helloWorldRepository.getHelloWorld()
    }
}
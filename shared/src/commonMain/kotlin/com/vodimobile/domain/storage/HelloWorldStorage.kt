package com.vodimobile.domain.storage

import com.vodimobile.domain.model.HelloWorld
import com.vodimobile.domain.usecase.GetHelloWorldUseCase

class HelloWorldStorage(
    private val getHelloWorldUseCase: GetHelloWorldUseCase
) {
    suspend fun get(): HelloWorld {
        return getHelloWorldUseCase.execute()
    }
}
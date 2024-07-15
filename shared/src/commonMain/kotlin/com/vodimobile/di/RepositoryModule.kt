package com.vodimobile.di

import com.vodimobile.data.repository.HelloWorldRepositoryImpl
import com.vodimobile.domain.repository.HelloWorldRepository
import com.vodimobile.domain.storage.HelloWorldStorage
import com.vodimobile.domain.usecase.GetHelloWorldUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedRepositoryModule = module {
    singleOf(::HelloWorldRepositoryImpl).bind<HelloWorldRepository>()

    single {
        HelloWorldStorage(getHelloWorldUseCase = GetHelloWorldUseCase(helloWorldRepository = get()))
    }
}
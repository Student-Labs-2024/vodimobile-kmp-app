package com.vodimobile.di

import com.vodimobile.data.repository.hash.HashRepositoryImpl
import com.vodimobile.domain.repository.hash.HashRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val hashModule = module {
    singleOf(::HashRepositoryImpl).bind<HashRepository>()
}
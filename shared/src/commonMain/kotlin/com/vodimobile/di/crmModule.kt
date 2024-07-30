package com.vodimobile.di

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val crmModule = module {
    singleOf(::CrmRepositoryImpl).bind<CrmRepository>()

    singleOf(::GetCarListUseCase)
    singleOf(::GetTariffListUseCase)

    singleOf(::CrmStorage)
}
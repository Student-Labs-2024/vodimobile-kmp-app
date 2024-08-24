package com.vodimobile.di

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.domain.repository.crm.CrmRepository
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.use_case.crm.CreateBidUseCase
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarFreeDateRange
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val crmModule = module {
    singleOf(::CrmRepositoryImpl).bind<CrmRepository>()

    singleOf(::GetCarListUseCase)
    singleOf(::GetTariffListUseCase)
    singleOf(::PostNewUserUseCase)
    singleOf(::GetAllPlacesUseCase)
    singleOf(::GetFreeCarsUseCaSE)
    singleOf(::GetServiceListUseCase)
    singleOf(::RefreshTokenUseCase)
    singleOf(::GetBidCostUseCase)
    singleOf(::GetCarFreeDateRange)
    singleOf(::CreateBidUseCase)

    singleOf(::CrmStorage).bind<CrmStorage>()
}

package com.vodimobile.di

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.utils.data_store.getDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {
    single { getDataStore(context = get()) }

    singleOf(::UserDataStoreRepositoryImpl).bind<UserDataStoreRepository>()

    singleOf(::EditUserDataStoreUseCase)
    singleOf(::GetUserDataUseCase)
    singleOf(::PreRegisterUserUseCase)
    singleOf(::EditPasswordUseCase)

    singleOf(::UserDataStoreStorage)
}

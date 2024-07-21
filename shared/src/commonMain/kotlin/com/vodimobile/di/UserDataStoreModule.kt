package com.vodimobile.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.vodimobile.domain.repository.data_store.UserDataStoreRepository
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import org.koin.dsl.bind

val userDataStoreRepositoryModule = module {
    singleOf(::UserDataStoreRepositoryImpl).bind<UserDataStoreRepository>()

    single {
        UserDataStoreStorage(
            editUserDataStoreUseCase = EditUserDataStoreUseCase(userDataStoreRepository = get()),
            getUserDataUseCase = GetUserDataUseCase(userDataStoreRepository = get())
        )
    }
}
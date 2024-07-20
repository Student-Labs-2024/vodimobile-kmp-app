package com.vodimobile.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.vodimobile.domain.repository.data_store.UserDataStore
import com.vodimobile.data.data_store.UserDataStoreImpl
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import org.koin.dsl.bind

val userDataStoreModule = module {
    singleOf(::UserDataStoreImpl).bind<UserDataStore>()

    single {
        UserDataStoreStorage(
            editUserDataStoreUseCase = EditUserDataStoreUseCase(userDataStore = get()),
            getUserDataUseCase = GetUserDataUseCase(userDataStore = get())
        )
    }
}
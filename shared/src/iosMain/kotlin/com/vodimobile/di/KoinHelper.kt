package com.vodimobile.di

import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    val userDataStoreStorage: UserDataStoreStorage by inject()

    companion object {
        fun initKoin() {
            com.vodimobile.di.initKoin()
        }
    }
}

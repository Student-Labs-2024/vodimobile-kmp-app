package com.vodimobile.di

import com.vodimobile.domain.model.User
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    companion object {
        fun initKoin() {
            com.vodimobile.di.initKoin()
        }
    }

    val userDataStoreStorage: UserDataStoreStorage by inject()

    suspend fun getUser(): User {
        var user: User = User.empty()
        userDataStoreStorage.getUser().collect { value ->
            user = value
        }
        return user
    }
}

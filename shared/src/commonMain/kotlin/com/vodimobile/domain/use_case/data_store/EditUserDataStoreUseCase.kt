package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.data_store.UserDataStore

class EditUserDataStoreUseCase(private val userDataStore: UserDataStore) {
    suspend fun execute(user: User) {
        userDataStore.editUserData(user = user)
    }
}
package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.data_store.UserDataStore
import kotlinx.coroutines.flow.Flow

class GetUserDataUseCase(private val userDataStore: UserDataStore) {
    suspend fun execute(): Flow<User> {
        return userDataStore.getUserData()
    }
}
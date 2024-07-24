package com.vodimobile.domain.repository.data_store

import com.vodimobile.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun editUserData(user: User)
    suspend fun getUserData() : Flow<User>
}
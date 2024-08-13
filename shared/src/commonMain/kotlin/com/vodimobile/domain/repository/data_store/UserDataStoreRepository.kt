package com.vodimobile.domain.repository.data_store

import com.vodimobile.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun editUserData(user: User)
    fun getUserData() : Flow<User>
    suspend fun editPreregister(name: String, password: String, id: Int)
    suspend fun editPassword(password: String)
}
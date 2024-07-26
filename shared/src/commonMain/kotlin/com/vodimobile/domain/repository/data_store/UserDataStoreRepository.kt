package com.vodimobile.domain.repository.data_store

import com.vodimobile.domain.model.User

interface UserDataStoreRepository {
    suspend fun editUserData(user: User)
    suspend fun getUserData() : User
}
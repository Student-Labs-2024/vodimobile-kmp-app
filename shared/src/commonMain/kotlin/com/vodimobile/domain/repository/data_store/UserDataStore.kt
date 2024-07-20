package com.vodimobile.domain.repository.data_store

import com.vodimobile.domain.model.User

interface UserDataStore {
    suspend fun editUserData(user: User)
}
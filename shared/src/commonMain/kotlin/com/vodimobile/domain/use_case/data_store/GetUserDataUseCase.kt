package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.data_store.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow

class GetUserDataUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend operator fun invoke(): Flow<User> {
        return userDataStoreRepository.getUserData()
    }
}
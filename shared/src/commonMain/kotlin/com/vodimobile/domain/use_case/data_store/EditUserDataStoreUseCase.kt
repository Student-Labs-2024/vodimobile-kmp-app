package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class EditUserDataStoreUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend fun execute(user: User) {
        userDataStoreRepository.editUserData(user = user)
    }
}
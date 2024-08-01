package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class EditPasswordUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend operator fun invoke(password: String) {
        userDataStoreRepository.editPassword(password)
    }
}
package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class EditLastAuthUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend operator fun invoke(lastAuth: Long) {
        userDataStoreRepository.editLastAuth(lastAuth = lastAuth)
    }
}
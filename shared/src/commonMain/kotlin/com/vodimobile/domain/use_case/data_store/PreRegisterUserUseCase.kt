package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class PreRegisterUserUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend operator fun invoke(name: String, password: String, id: Int) {
        userDataStoreRepository.editPreregister(name, password, id)
    }
}
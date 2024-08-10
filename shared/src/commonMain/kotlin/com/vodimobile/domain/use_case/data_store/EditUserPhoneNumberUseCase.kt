package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class EditUserPhoneNumberUseCase(private val userDataStoreRepository: UserDataStoreRepository) {
    suspend operator fun invoke(phone: String) {
        userDataStoreRepository.editUserPhoneNumber(phone = phone)
    }
}
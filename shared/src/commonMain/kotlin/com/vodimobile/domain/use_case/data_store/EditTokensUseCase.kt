package com.vodimobile.domain.use_case.data_store

import com.vodimobile.domain.repository.data_store.UserDataStoreRepository

class EditTokensUseCase(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String, expires: Long) {
        userDataStoreRepository.editTokens(accessToken, refreshToken, expires)
    }
}
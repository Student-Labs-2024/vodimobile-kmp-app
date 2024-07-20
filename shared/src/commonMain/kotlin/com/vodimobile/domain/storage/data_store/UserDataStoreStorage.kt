package com.vodimobile.domain.storage.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase

class UserDataStoreStorage(
    private val editUserDataStoreUseCase: EditUserDataStoreUseCase
) {
    suspend fun edit(user: User) {
        editUserDataStoreUseCase.execute(user = user)
    }
}
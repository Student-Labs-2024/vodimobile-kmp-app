package com.vodimobile.domain.storage.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import kotlinx.coroutines.flow.Flow

class UserDataStoreStorage(
    private val editUserDataStoreUseCase: EditUserDataStoreUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) {
    suspend fun edit(user: User) {
        editUserDataStoreUseCase.execute(user = user)
    }

    suspend fun getUser(): User {
        return getUserDataUseCase.execute()
    }
}
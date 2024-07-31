package com.vodimobile.domain.storage.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import kotlinx.coroutines.flow.Flow

class UserDataStoreStorage(
    private val editUserDataStoreUseCase: EditUserDataStoreUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val preRegisterUserUseCase: PreRegisterUserUseCase,
    private val editPasswordUseCase: EditPasswordUseCase
) {
    suspend fun edit(user: User) {
        editUserDataStoreUseCase(user = user)
    }

    fun getUser(): Flow<User> {
        return getUserDataUseCase()
    }

    suspend fun preregister(name: String, password: String, token: String) {
        preRegisterUserUseCase(name, password, token)
    }

    suspend fun editPassword(password: String, token: String) {
        editPasswordUseCase(password, token)
    }
}
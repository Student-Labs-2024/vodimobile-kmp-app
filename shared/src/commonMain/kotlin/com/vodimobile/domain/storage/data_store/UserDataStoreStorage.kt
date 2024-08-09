package com.vodimobile.domain.storage.data_store

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.data_store.EditLastAuthUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditTokensUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import kotlinx.coroutines.flow.Flow

class UserDataStoreStorage(
    private val editUserDataStoreUseCase: EditUserDataStoreUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val preRegisterUserUseCase: PreRegisterUserUseCase,
    private val editPasswordUseCase: EditPasswordUseCase,
    private val editTokensUseCase: EditTokensUseCase,
    private val editLastAuthUseCase: EditLastAuthUseCase
) {
    suspend fun edit(user: User) {
        editUserDataStoreUseCase(user = user)
    }

    fun getUser(): Flow<User> {
        return getUserDataUseCase()
    }

    suspend fun preregister(
        name: String,
        password: String,
        accessToken: String,
        refreshToken: String,
        expired: Long
    ) {
        preRegisterUserUseCase(name, password, accessToken, refreshToken, expired)
    }

    suspend fun editPassword(password: String) {
        editPasswordUseCase(password)
    }

    suspend fun editTokens(accessToken: String, refreshToken: String, expires: Long) {
        editTokensUseCase(accessToken, refreshToken, expires)
    }

    suspend fun editLastAuth(lastAuth: Long) {
        editLastAuthUseCase.invoke(lastAuth)
    }
}
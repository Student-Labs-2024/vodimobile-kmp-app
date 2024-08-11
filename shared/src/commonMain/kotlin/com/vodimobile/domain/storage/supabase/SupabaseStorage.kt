package com.vodimobile.domain.storage.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase

class SupabaseStorage(
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val updateFullNameUseCase: UpdateFullNameUseCase,
    private val updatePhoneUseCase: UpdatePhoneUseCase,
    private val updateTokensUseCase: UpdateTokensUseCase
) {
    suspend fun getUser(password: String, phone: String) =
        getUserUseCase(password = password, phone = phone)

    suspend fun insertUser(user: User) = insertUserUseCase(user = user)

    suspend fun updatePassword(userId: Int, password: String) =
        updatePasswordUseCase(userId, password)

    suspend fun updateFullName(userId: Int, fullName: String) =
        updateFullNameUseCase(userId, fullName)

    suspend fun updatePhone(userId: Int, phone: String) = updatePhoneUseCase(userId, phone)
    suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String) =
        updateTokensUseCase(userId, accessToken, refreshToken)
}
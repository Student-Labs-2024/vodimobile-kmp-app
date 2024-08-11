package com.vodimobile.domain.storage.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase

class SupabaseStorage(
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase
) {
    suspend fun getUser(password: String, phone: String) =
        getUserUseCase(password = password, phone = phone)

    suspend fun insertUser(user: User) = insertUserUseCase(user = user)
}
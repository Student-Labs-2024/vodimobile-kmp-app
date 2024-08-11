package com.vodimobile.domain.repository.supabase

import com.vodimobile.domain.model.supabase.UserDTO

interface SupabaseRepository {
    suspend fun getUser(password: String, phone: String) : UserDTO
    suspend fun insertUser(userDTO: UserDTO)
    suspend fun insertPassword(userId: Int, password: String)
    suspend fun insertFullName(userId: Int,fullName: String)
    suspend fun insertPhone(userId: Int,phone: String)
    suspend fun insertAccessToken(userId: Int,accessToken: String)
    suspend fun insertRefreshToken(userId: Int,refreshToken: String)
}
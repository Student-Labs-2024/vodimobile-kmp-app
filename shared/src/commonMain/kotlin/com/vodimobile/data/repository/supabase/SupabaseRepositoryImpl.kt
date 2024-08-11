package com.vodimobile.data.repository.supabase

import com.vodimobile.domain.client.provideSupabaseClient
import com.vodimobile.domain.model.supabase.UserDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import io.github.jan.supabase.postgrest.from

class SupabaseRepositoryImpl : SupabaseRepository {

    private val supabaseClient = provideSupabaseClient()

    override suspend fun getUser(password: String, phone: String): UserDTO {
        val listUserDTO: List<UserDTO> =
            supabaseClient.from(SupabaseTables.USER_TABLE).select().decodeList<UserDTO>()
        val userDTO: UserDTO =
            listUserDTO.find { it.password == password && it.phone == phone } ?: UserDTO.empty()
        return userDTO
    }

    override suspend fun insertUser(userDTO: UserDTO) {
        with(userDTO) {
            supabaseClient.from(SupabaseTables.USER_TABLE).insert(
                mapOf(
                    SupabaseColumns.User.PASSWORD to password,
                    SupabaseColumns.User.PHONE to phone,
                    SupabaseColumns.User.ACCESS_TOKEN to access_token,
                    SupabaseColumns.User.REFRESH_TOKEN to refresh_token,
                    SupabaseColumns.User.FULL_NAME to full_name,
                )
            ) { single() }
        }
    }

    override suspend fun insertPassword(userId: Int, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertFullName(userId: Int, fullName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertPhone(userId: Int, phone: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAccessToken(userId: Int, accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertRefreshToken(userId: Int, refreshToken: String) {
        TODO("Not yet implemented")
    }
}
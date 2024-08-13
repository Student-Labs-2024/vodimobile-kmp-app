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

    override suspend fun updatePassword(userId: Int, password: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PASSWORD, password)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateFullName(userId: Int, fullName: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.FULL_NAME, fullName)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updatePhone(userId: Int, phone: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PHONE, phone)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.ACCESS_TOKEN, accessToken)
            set(SupabaseColumns.User.REFRESH_TOKEN, refreshToken)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateUser(userDTO: UserDTO) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PHONE, userDTO.phone)
            set(SupabaseColumns.User.PASSWORD, userDTO.password)
            set(SupabaseColumns.User.ACCESS_TOKEN, userDTO.access_token)
            set(SupabaseColumns.User.REFRESH_TOKEN, userDTO.refresh_token)
            set(SupabaseColumns.User.FULL_NAME, userDTO.full_name)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userDTO.user_id)
            }
        }
    }
}
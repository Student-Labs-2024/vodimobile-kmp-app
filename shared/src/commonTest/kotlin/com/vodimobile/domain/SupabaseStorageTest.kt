package com.vodimobile.domain

import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.supabase.UserDTO
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SupabaseStorageTest {

    @Test
    fun getUserTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()

            val userDTO: UserDTO = supabaseRepository.getUser(password = "12345", phone = "+00000000000")
            assertEquals(userDTO.full_name, "Test")
        }
    }

    @Test
    fun insertUserTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()

            val userDTO: UserDTO = UserDTO(
                user_id = -100,
                password = "qwerty",
                phone = "+00000000000",
                access_token = "hello",
                refresh_token = "world",
                full_name = "Slava Test User"
            )
            supabaseRepository.insertUser(userDTO)

            val user: UserDTO =
                supabaseRepository.getUser(password = "qwerty", phone = "+00000000000")
            assertEquals(user.full_name, "Slava Test User")
        }
    }
}
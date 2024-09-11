package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.supabase.UserDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class GetUserUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(password: String, phone: String): User {
        val userDTO: UserDTO = supabaseRepository.getUser(password = password, phone = phone)
        return User(
            id = userDTO.user_id,
            fullName = userDTO.full_name,
            password = userDTO.password,
            accessToken = userDTO.access_token,
            refreshToken = userDTO.refresh_token,
            phone = userDTO.phone
        )
    }
}

package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.User.Companion.toSupabaseUserDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class InsertUserUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(user: User) {
        supabaseRepository.insertUser(user.toSupabaseUserDTO())
    }
}
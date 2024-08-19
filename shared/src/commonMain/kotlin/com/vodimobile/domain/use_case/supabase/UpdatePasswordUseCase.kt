package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdatePasswordUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, password: String) {
        supabaseRepository.updatePassword(userId, password)
    }
}
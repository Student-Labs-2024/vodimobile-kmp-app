package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdateFullNameUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, fullName: String) {
        supabaseRepository.updateFullName(userId, fullName)
    }
}

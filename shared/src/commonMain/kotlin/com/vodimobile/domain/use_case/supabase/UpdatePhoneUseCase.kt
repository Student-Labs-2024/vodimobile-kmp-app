package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdatePhoneUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, phone: String) {
        supabaseRepository.updatePhone(userId, phone)
    }
}

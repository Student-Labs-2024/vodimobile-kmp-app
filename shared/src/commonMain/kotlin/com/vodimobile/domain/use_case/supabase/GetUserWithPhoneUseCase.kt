package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class GetUserWithPhoneUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(phone: String): User {
        return supabaseRepository.getUserWithPhone(phone = phone)
    }
}

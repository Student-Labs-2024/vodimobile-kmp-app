package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.supabase.UserDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class HasUserWithPhoneUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(phone: String): Boolean {
        return supabaseRepository.hasUserWithPhone(phone = phone)
    }
}

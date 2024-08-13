package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdateTokensUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, accessToken: String, refreshToken: String) {
        supabaseRepository.updateTokens(userId, accessToken, refreshToken)
    }
}
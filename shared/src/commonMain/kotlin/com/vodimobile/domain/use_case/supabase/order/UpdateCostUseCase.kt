package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdateCostUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, orderId: Int, coast: Float) {
        supabaseRepository.updateCost(userId, orderId, coast)
    }
}

package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdateCrmOrderUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, orderId: Int, crmOrder: Int) {
        supabaseRepository.updateCrmOrder(userId, orderId, crmOrder)
    }
}

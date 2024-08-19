package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdateServicesUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, orderId: Int, services: String) {
        supabaseRepository.updateServices(userId, orderId, services)
    }
}

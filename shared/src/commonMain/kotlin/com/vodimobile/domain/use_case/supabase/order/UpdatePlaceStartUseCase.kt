package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdatePlaceStartUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, orderId: Int, placeStart: String) {
        supabaseRepository.updatePlaceStart(userId, orderId, placeStart)
    }
}

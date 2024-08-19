package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.repository.supabase.SupabaseRepository

class UpdatePlaceFinishUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int, orderId: Int, placeFinish: String) {
        supabaseRepository.updatePlaceFinish(userId, orderId, placeFinish)
    }
}

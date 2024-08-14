package com.vodimobile.domain.use_case.supabase

import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class GetOrdersUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(userId: Int): List<OrderDTO> {
        return supabaseRepository.getOrders(userId = userId)
    }
}
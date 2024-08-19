package com.vodimobile.domain.use_case.supabase.order

import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository

class InsertOrderUseCase(private val supabaseRepository: SupabaseRepository) {
    suspend operator fun invoke(orderDTO: OrderDTO) {
        supabaseRepository.insertOrder(orderDTO = orderDTO)
    }
}
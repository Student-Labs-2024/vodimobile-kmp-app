package com.vodimobile.domain.repository.supabase

import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.model.supabase.UserDTO

interface SupabaseRepository {
    suspend fun getUser(password: String, phone: String) : UserDTO
    suspend fun insertUser(userDTO: UserDTO)
    suspend fun updatePassword(userId: Int, password: String)
    suspend fun updateFullName(userId: Int,fullName: String)
    suspend fun updatePhone(userId: Int,phone: String)
    suspend fun updateTokens(userId: Int,accessToken: String, refreshToken: String)
    suspend fun updateUser(userDTO: UserDTO)

    suspend fun insertOrder(orderDTO: OrderDTO)
    suspend fun getOrders(userId: Int) : List<OrderDTO>
}
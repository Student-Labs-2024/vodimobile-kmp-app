package com.vodimobile.domain.repository.supabase

import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.model.supabase.UserDTO

interface SupabaseRepository {
    suspend fun getUser(password: String, phone: String): UserDTO
    suspend fun insertUser(userDTO: UserDTO)
    suspend fun updatePassword(userId: Int, password: String)
    suspend fun updateFullName(userId: Int, fullName: String)
    suspend fun updatePhone(userId: Int, phone: String)
    suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String)
    suspend fun updateUser(userDTO: UserDTO)

    suspend fun insertOrder(orderDTO: OrderDTO)
    suspend fun getOrders(userId: Int): List<OrderDTO>
    suspend fun updateOrderStatus(userId: Int, orderId: Int, status: String)
    suspend fun updateNumber(userId: Int, orderId: Int, number: Int)
    suspend fun updateCrmOrder(userId: Int, orderId: Int, crmOrder: Int)
    suspend fun updatePlaceStart(userId: Int, orderId: Int, placeStart: String)
    suspend fun updatePlaceFinish(userId: Int, orderId: Int, placeFinish: String)
    suspend fun updateCost(userId: Int, orderId: Int, coast: Float)
    suspend fun updateServices(userId: Int, orderId: Int, services: String)
}

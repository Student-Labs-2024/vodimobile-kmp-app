package com.vodimobile.domain.storage.supabase

import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.GetUserWithPhoneUseCase
import com.vodimobile.domain.use_case.supabase.HasUserWithPhoneUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCostUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCrmOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateNumberUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceFinishUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceStartUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateServicesUseCase

class SupabaseStorage(
    private val getUserUseCase: GetUserUseCase,
    private val hasUserWithPhoneUseCase: HasUserWithPhoneUseCase,
    private val getUsersWithPhoneUseCase: GetUserWithPhoneUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val updateFullNameUseCase: UpdateFullNameUseCase,
    private val updatePhoneUseCase: UpdatePhoneUseCase,
    private val updateTokensUseCase: UpdateTokensUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
    private val updateNumberUseCase: UpdateNumberUseCase,
    private val updateCrmOrderUseCase: UpdateCrmOrderUseCase,
    private val updatePlaceStartUseCase: UpdatePlaceStartUseCase,
    private val updatePlaceFinishUseCase: UpdatePlaceFinishUseCase,
    private val updateCostUseCase: UpdateCostUseCase,
    private val updateServicesUseCase: UpdateServicesUseCase
) {
    suspend fun getUser(password: String, phone: String) =
        getUserUseCase(password = password, phone = phone)

    suspend fun getUserWithPhone(phone: String) =
        getUsersWithPhoneUseCase(phone = phone)

    suspend fun hasUserWithPhone(phone: String) = hasUserWithPhoneUseCase(phone = phone)

    suspend fun insertUser(user: User) = insertUserUseCase(user = user)

    suspend fun updatePassword(userId: Int, password: String) =
        updatePasswordUseCase(userId, password)

    suspend fun updateFullName(userId: Int, fullName: String) =
        updateFullNameUseCase(userId, fullName)

    suspend fun updatePhone(userId: Int, phone: String) = updatePhoneUseCase(userId, phone)
    suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String) =
        updateTokensUseCase(userId, accessToken, refreshToken)

    suspend fun insertOrder(orderDTO: OrderDTO) = insertOrderUseCase(orderDTO = orderDTO)
    suspend fun getOrders(userId: Int, accessToken: String, refreshToken: String, phone: String) =
        getOrdersUseCase(userId = userId, accessToken = accessToken, refreshToken = refreshToken, phone = phone)

    suspend fun updateStatus(userId: Int, orderId: Int, status: String) =
        updateOrderStatusUseCase(userId, orderId, status)

    suspend fun updateNumber(userId: Int, orderId: Int, number: Int) =
        updateNumberUseCase(userId, orderId, number)

    suspend fun updateCrmOrder(userId: Int, orderId: Int, crmOrder: Int) =
        updateCrmOrderUseCase(userId, orderId, crmOrder)

    suspend fun updatePlaceStart(userId: Int, orderId: Int, placeStart: String) =
        updatePlaceStartUseCase(userId, orderId, placeStart)

    suspend fun updatePlaceFinish(userId: Int, orderId: Int, placeFinish: String) =
        updatePlaceFinishUseCase(userId, orderId, placeFinish)

    suspend fun updateCost(userId: Int, orderId: Int, coast: Float) =
        updateCostUseCase(userId, orderId, coast)

    suspend fun updateServices(userId: Int, orderId: Int, services: String) =
        updateServicesUseCase(userId, orderId, services)
}

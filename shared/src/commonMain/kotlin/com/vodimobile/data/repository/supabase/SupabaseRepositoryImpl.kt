package com.vodimobile.data.repository.supabase

import com.vodimobile.domain.client.provideSupabaseClient
import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.model.supabase.UserDTO
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import io.github.jan.supabase.postgrest.from

class SupabaseRepositoryImpl : SupabaseRepository {

    private val supabaseClient = provideSupabaseClient()

    override suspend fun getUser(password: String, phone: String): UserDTO {
        val listUserDTO: List<UserDTO> =
            supabaseClient.from(SupabaseTables.USER_TABLE).select().decodeList<UserDTO>()
        val userDTO: UserDTO =
            listUserDTO.find { it.password == password && it.phone == phone } ?: UserDTO.empty()
        return userDTO
    }

    override suspend fun insertUser(userDTO: UserDTO) {
        with(userDTO) {
            supabaseClient.from(SupabaseTables.USER_TABLE).insert(
                mapOf(
                    SupabaseColumns.User.PASSWORD to password,
                    SupabaseColumns.User.PHONE to phone,
                    SupabaseColumns.User.ACCESS_TOKEN to access_token,
                    SupabaseColumns.User.REFRESH_TOKEN to refresh_token,
                    SupabaseColumns.User.FULL_NAME to full_name,
                )
            ) { single() }
        }
    }

    override suspend fun updatePassword(userId: Int, password: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PASSWORD, password)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateFullName(userId: Int, fullName: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.FULL_NAME, fullName)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updatePhone(userId: Int, phone: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PHONE, phone)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateTokens(userId: Int, accessToken: String, refreshToken: String) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.ACCESS_TOKEN, accessToken)
            set(SupabaseColumns.User.REFRESH_TOKEN, refreshToken)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userId)
            }
        }
    }

    override suspend fun updateUser(userDTO: UserDTO) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update({
            set(SupabaseColumns.User.PHONE, userDTO.phone)
            set(SupabaseColumns.User.PASSWORD, userDTO.password)
            set(SupabaseColumns.User.ACCESS_TOKEN, userDTO.access_token)
            set(SupabaseColumns.User.REFRESH_TOKEN, userDTO.refresh_token)
            set(SupabaseColumns.User.FULL_NAME, userDTO.full_name)
        }) {
            filter {
                eq(SupabaseColumns.User.USER_ID, userDTO.user_id)
            }
        }
    }

    override suspend fun insertOrder(orderDTO: OrderDTO) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).insert(
            mapOf(
                SupabaseColumns.Orders.USER_ID to orderDTO.user_id,
                SupabaseColumns.Orders.BID_NUMBER to orderDTO.bid_number,
                SupabaseColumns.Orders.CRM_BID_ID to orderDTO.crm_bid_id,
                SupabaseColumns.Orders.CAR_ID to orderDTO.car_id,
            )
        ) { single() }


        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update({
            set(SupabaseColumns.Orders.BID_STATUS, orderDTO.bid_status)
            set(SupabaseColumns.Orders.DATE_START, orderDTO.date_start)
            set(SupabaseColumns.Orders.TIME_START, orderDTO.time_start)
            set(SupabaseColumns.Orders.DATE_FINISH, orderDTO.date_finish)
            set(SupabaseColumns.Orders.TIME_FINISH, orderDTO.time_finish)
            set(SupabaseColumns.Orders.PLACE_START, orderDTO.place_start)
            set(SupabaseColumns.Orders.PLACE_FINISH, orderDTO.place_finish)
            set(SupabaseColumns.Orders.COST, orderDTO.cost)
            set(SupabaseColumns.Orders.SERVICES, orderDTO.services)
        }) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, orderDTO.user_id)
                eq(SupabaseColumns.Orders.BID_NUMBER, orderDTO.bid_number)
            }
        }
    }

    override suspend fun getOrders(userId: Int): List<OrderDTO> {
        val ordersDTO =
            supabaseClient.from(SupabaseTables.ORDERS_TABLE).select().decodeList<OrderDTO>()
                .filter { it.user_id == userId }

        return ordersDTO
    }

    override suspend fun updateOrderStatus(userId: Int, orderId: Int, status: String) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.BID_STATUS, status)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override suspend fun updateNumber(userId: Int, orderId: Int, number: Int) {
        supabaseClient.from(SupabaseTables.USER_TABLE).update(
            {
                set(SupabaseColumns.Orders.BID_NUMBER, number)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override suspend fun updateCrmOrder(userId: Int, orderId: Int, crmOrder: Int) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.CRM_BID_ID, crmOrder)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override suspend fun updatePlaceStart(userId: Int, orderId: Int, placeStart: String) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.PLACE_START, placeStart)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override suspend fun updatePlaceFinish(userId: Int, orderId: Int, placeFinish: String) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.PLACE_FINISH, placeFinish)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override suspend fun updateCost(userId: Int, orderId: Int, coast: Float) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.COST, coast)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }

    override  suspend fun updateServices(userId: Int, orderId: Int, services: String) {
        supabaseClient.from(SupabaseTables.ORDERS_TABLE).update(
            {
                set(SupabaseColumns.Orders.SERVICES, services)
            }
        ) {
            filter {
                eq(SupabaseColumns.Orders.USER_ID, userId)
                eq(SupabaseColumns.Orders.BID_ID, orderId)
            }
        }
    }
}

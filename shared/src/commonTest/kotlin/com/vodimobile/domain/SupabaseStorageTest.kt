package com.vodimobile.domain

import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.model.supabase.UserDTO
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SupabaseStorageTest {

    @Test
    fun getUserTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()

            val userDTO: UserDTO =
                supabaseRepository.getUser(password = "12345", phone = "+00000000000")
            assertEquals(userDTO.full_name, "Test")
        }
    }

    @Test
    fun insertUserTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()

            val userDTO: UserDTO = UserDTO(
                user_id = -100,
                password = "qwerty",
                phone = "+00000000004",
                access_token = "hello",
                refresh_token = "world",
                full_name = "Slava Test User"
            )
            supabaseRepository.insertUser(userDTO)

            val user: UserDTO =
                supabaseRepository.getUser(password = "qwerty", phone = "+00000000002")
            assertEquals(user.full_name, "Slava Test User")
        }
    }

    @Test
    fun updateTestUserTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()
            supabaseRepository.updateFullName(userId = 2, fullName = "Слава Дейч")

            val userDTO: UserDTO =
                supabaseRepository.getUser(password = "qwerty", phone = "+00000000000")
            assertEquals(userDTO.full_name, "Слава Дейч")
        }
    }

    @Test
    fun insertOrderTest() {
        runBlocking {
            val testOrderDTO = OrderDTO(
                bid_id = -1,
                user_id = 0,
                bid_number = 0,
                crm_bid_id = 0,
                car_id = 17,
                bid_status = "Test",
                date_start = "01.01.2024",
                time_start = "19:00",
                date_finish = "13.08.2024",
                time_finish = "20:00",
                place_start = "Жд вокзал",
                place_finish = "Жд вокзал",
                cost = 123.0f,
                services = arrayOf(1, 2, 3).joinToString(", ") { it.toString() },
            )

            val supabaseRepository = SupabaseRepositoryImpl()
            supabaseRepository.insertOrder(orderDTO = testOrderDTO)

        }
    }

    @Test
    fun getOrdersTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()
            val orders = supabaseRepository.getOrders(userId = 0)
            assertTrue(orders.isNotEmpty())
        }
    }
}
//Well, We were told that we don't have a blank line at the end of the file. Here it is! The super important part of our module is right here!

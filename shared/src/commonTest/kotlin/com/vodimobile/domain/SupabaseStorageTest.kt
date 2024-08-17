package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.supabase.OrderDTO
import com.vodimobile.domain.model.supabase.UserDTO
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.domain.use_case.crm.CreateBidUseCase
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarFreeDateRange
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
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
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
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

    @Test
    fun getOrdersUsingSupabaseStorageTest() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )
            val supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(supabaseRepository),
                insertOrderUseCase = InsertOrderUseCase(supabaseRepository),
                insertUserUseCase = InsertUserUseCase(supabaseRepository),
                updateFullNameUseCase = UpdateFullNameUseCase(supabaseRepository),
                updatePasswordUseCase = UpdatePasswordUseCase(supabaseRepository),
                updatePhoneUseCase = UpdatePhoneUseCase(supabaseRepository),
                updateTokensUseCase = UpdateTokensUseCase(supabaseRepository),
                getOrdersUseCase = GetOrdersUseCase(supabaseRepository, crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )
            val orders = supabaseStorage.getOrders(
                userId = 0,
                accessToken = SharedBuildkonfig.crm_test_access_token,
                refreshToken = SharedBuildkonfig.crm_test_refresh_token
            )
            assertTrue(orders.isNotEmpty())
        }
    }

    @Test
    fun updateOrderStatus() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )
            val supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(supabaseRepository),
                insertOrderUseCase = InsertOrderUseCase(supabaseRepository),
                insertUserUseCase = InsertUserUseCase(supabaseRepository),
                updateFullNameUseCase = UpdateFullNameUseCase(supabaseRepository),
                updatePasswordUseCase = UpdatePasswordUseCase(supabaseRepository),
                updatePhoneUseCase = UpdatePhoneUseCase(supabaseRepository),
                updateTokensUseCase = UpdateTokensUseCase(supabaseRepository),
                getOrdersUseCase = GetOrdersUseCase(supabaseRepository, crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updateStatus(userId = 0, orderId = 11, status = "В обработке")
        }
    }

    @Test
    fun updatePlaceStart() {
        runBlocking {
            val supabaseRepository = SupabaseRepositoryImpl()
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )
            val supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(supabaseRepository),
                insertOrderUseCase = InsertOrderUseCase(supabaseRepository),
                insertUserUseCase = InsertUserUseCase(supabaseRepository),
                updateFullNameUseCase = UpdateFullNameUseCase(supabaseRepository),
                updatePasswordUseCase = UpdatePasswordUseCase(supabaseRepository),
                updatePhoneUseCase = UpdatePhoneUseCase(supabaseRepository),
                updateTokensUseCase = UpdateTokensUseCase(supabaseRepository),
                getOrdersUseCase = GetOrdersUseCase(supabaseRepository, crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updatePlaceStart(userId = 2, orderId = 5, placeStart = "В городе")
        }
    }
}
//Well, We were told that we don't have a blank line at the end of the file. Here it is! The super important part of our module is right here!

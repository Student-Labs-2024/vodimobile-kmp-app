package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
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
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCostUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCrmOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateNumberUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceFinishUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceStartUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateServicesUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class SupabaseOrderStorageTest {

    @Test
    fun updateOrderStatusUsingSupabaseStorageTest() {
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
                getOrdersUseCase = GetOrdersUseCase(
                    supabaseRepository,
                    crmStorage,
                    CrmRepositoryImpl()
                ),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updateStatus(userId = 0, orderId = 11, status = "Test")
        }
    }

    @Test
    fun updateOrderCostUsingSupabaseStorageTest() {
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
                getOrdersUseCase = GetOrdersUseCase(
                    supabaseRepository,
                    crmStorage,
                    CrmRepositoryImpl()
                ),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updateCost(userId = 0, orderId = 11, coast = 100.0f)
        }
    }

    @Test
    fun updateServicesOrderUsingSupabaseStorageTest() {
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
                getOrdersUseCase = GetOrdersUseCase(
                    supabaseRepository,
                    crmStorage,
                    CrmRepositoryImpl()
                ),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updateServices(userId = 0, orderId = 11, services = "1, 2, 3")
        }
    }

    @Test
    fun updatePlaceStartOrderUsingSupabaseStorageTest() {
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
                getOrdersUseCase = GetOrdersUseCase(
                    supabaseRepository,
                    crmStorage,
                    CrmRepositoryImpl()
                ),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updatePlaceStart(userId = 0, orderId = 11, placeStart = "Офис")
        }
    }

    @Test
    fun updatePlaceFinishOrderUsingSupabaseStorageTest() {
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
                getOrdersUseCase = GetOrdersUseCase(
                    supabaseRepository,
                    crmStorage,
                    CrmRepositoryImpl()
                ),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(supabaseRepository),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )

            supabaseStorage.updatePlaceFinish(userId = 0, orderId = 11, placeFinish = "Офис")
        }
    }
}
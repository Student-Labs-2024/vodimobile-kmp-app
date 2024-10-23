package com.vodimobile.domain

import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.hash.HashRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.User
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
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class SupabaseUserStorageTest {

    @Test
    fun getUserUsingSupabaseStorageTest() {
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
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
            )
            val hashRepository = HashRepositoryImpl()

            val user: User =
                supabaseStorage.getUser(
                    password = hashRepository.hash(text = "HelloWorld1!").decodeToString(),
                    phone = "+71111111111"
                )
            assertEquals(user.fullName, "Slava")
        }
    }

    @Test
    fun updateUserUsingSupabaseStorageTest() {
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
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
            )
            val hashRepository = HashRepositoryImpl()

            supabaseStorage.updateFullName(userId = 293, fullName = "Slava")

            val userFromRemote =
                supabaseStorage.getUser(
                    password = hashRepository.hash(text = "HelloWorld1!").decodeToString(),
                    phone = "+71111111111"
                )
            assertEquals(actual = userFromRemote.fullName, expected = "Slava")
        }
    }
}
//Well, We were told that we don't have a blank line at the end of the file. Here it is! The super important part of our module is right here!

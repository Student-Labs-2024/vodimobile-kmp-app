package com.vodimobile.di

import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import com.vodimobile.domain.storage.supabase.SupabaseStorage
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
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val supabaseModule = module {
    singleOf(::SupabaseRepositoryImpl).bind<SupabaseRepository>()

    singleOf(::GetUserUseCase)
    singleOf(::HasUserWithPhoneUseCase)
    singleOf(::InsertUserUseCase)
    singleOf(::UpdatePhoneUseCase)
    singleOf(::UpdateTokensUseCase)
    singleOf(::UpdatePasswordUseCase)
    singleOf(::UpdateFullNameUseCase)
    singleOf(::InsertUserUseCase)
    singleOf(::GetOrdersUseCase)
    singleOf(::InsertOrderUseCase)
    singleOf(::UpdateOrderStatusUseCase)
    singleOf(::UpdateNumberUseCase)
    singleOf(::UpdateCrmOrderUseCase)
    singleOf(::UpdatePlaceStartUseCase)
    singleOf(::UpdatePlaceFinishUseCase)
    singleOf(::UpdateCostUseCase)
    singleOf(::UpdateServicesUseCase)

    singleOf(::SupabaseStorage)
}

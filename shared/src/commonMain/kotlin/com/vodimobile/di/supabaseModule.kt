package com.vodimobile.di

import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.repository.supabase.SupabaseRepository
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.domain.use_case.supabase.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val supabaseModule = module {
    singleOf(::SupabaseRepositoryImpl).bind<SupabaseRepository>()

    singleOf(::GetUserUseCase)
    singleOf(::InsertUserUseCase)
    singleOf(::UpdatePhoneUseCase)
    singleOf(::UpdateTokensUseCase)
    singleOf(::UpdatePasswordUseCase)
    singleOf(::UpdateFullNameUseCase)
    singleOf(::InsertUserUseCase)
    singleOf(::GetOrdersUseCase)

    singleOf(::SupabaseStorage)
}
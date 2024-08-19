package com.vodimobile.domain.client

import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

fun provideSupabaseClient() = createSupabaseClient(
    supabaseUrl = SharedBuildkonfig.supabase_url,
    supabaseKey = SharedBuildkonfig.supabase_public
) {
    install(Postgrest)
}
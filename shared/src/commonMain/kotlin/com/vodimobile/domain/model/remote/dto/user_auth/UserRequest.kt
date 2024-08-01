package com.vodimobile.domain.model.remote.dto.user_auth

import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val UserName: String = SharedBuildkonfig.crm_login,
    val PasswordHash: String = SharedBuildkonfig.crm_password_hash,
    val LongToken: Boolean = true
)

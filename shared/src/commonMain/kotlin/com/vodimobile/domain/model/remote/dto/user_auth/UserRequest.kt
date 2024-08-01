package com.vodimobile.domain.model.remote.dto.user_auth

import com.vodimobile.shared.buildkonfig.SharedBuildkonfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    @SerialName("UserName") val UserName: String = SharedBuildkonfig.crm_login,
    @SerialName("PasswordHash") val PasswordHash: String = SharedBuildkonfig.crm_password_hash,
    @SerialName("LongToken") val LongToken: Boolean = true
)

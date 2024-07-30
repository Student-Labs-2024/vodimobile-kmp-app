package com.vodimobile.domain.model.remote.dto.user_auth

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val UserName: String,
    val PasswordHash: String,
    val LongToken: Boolean = true
)

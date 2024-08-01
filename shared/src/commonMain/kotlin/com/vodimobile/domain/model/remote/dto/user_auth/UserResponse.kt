package com.vodimobile.domain.model.remote.dto.user_auth

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val accessToken: String,
    val refreshToken: String,
    val expires: Long
)
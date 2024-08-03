package com.vodimobile.domain.model.remote.dto.refresh_token

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val Token: String
)

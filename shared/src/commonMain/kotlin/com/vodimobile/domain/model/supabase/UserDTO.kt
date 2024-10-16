package com.vodimobile.domain.model.supabase

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val user_id: Int,
    val password: String,
    val phone: String,
    val access_token: String,
    val refresh_token: String,
    val full_name: String,
    val aes_key: String
) {
    companion object {
        fun empty() = UserDTO(
            user_id = -1,
            password = "",
            phone = "",
            access_token = "",
            refresh_token = "",
            full_name = "",
            aes_key = ""
        )
    }
}

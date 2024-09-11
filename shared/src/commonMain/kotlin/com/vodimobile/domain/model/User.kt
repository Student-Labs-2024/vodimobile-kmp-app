package com.vodimobile.domain.model

import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest
import com.vodimobile.domain.model.supabase.UserDTO

data class User(
    val id: Int,
    val fullName: String,
    val password: String,
    val accessToken: String,
    val refreshToken: String,
    val phone: String
) {

    constructor(
        id: Int,
        fullName: String,
        password: String,
        phone: String
    ) : this(-1, fullName, password, "", "", phone)

    constructor(
        id: Int,
        fullName: String,
        password: String,
        accessToken: String,
        refreshToken: String,
    ) : this (-1, fullName, password, accessToken, refreshToken, "")

    constructor(
        id: Int,
        fullName: String,
        password: String,
    ) : this (-1, fullName, password, "", "", "")

    companion object {
        fun empty(): User {
            return User(
                -1,
                "",
                "",
                "",
                "",
                "",
            )
        }

        fun User.toUserRequest(longToken: Boolean = false): UserRequest {
            return UserRequest(
                UserName = this.fullName,
                PasswordHash = this.password.hashCode().toString(),
                LongToken = longToken
            )
        }

        fun User.toSupabaseUserDTO(): UserDTO {
            return UserDTO(
                user_id = this.id,
                password = this.password,
                full_name = this.fullName,
                access_token = this.accessToken,
                refresh_token = this.refreshToken,
                phone = this.phone
            )
        }
    }
}

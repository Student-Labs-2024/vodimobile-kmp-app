package com.vodimobile.domain.model

import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest

data class User(
    val fullName: String,
    val password: String,
    val accessToken: String,
    val refreshToken: String,
    val expires: Long,
    val phone: String,
    val email: String?
) {

    constructor(
        fullName: String,
        password: String,
        phone: String,
        email: String? = null
    ) : this(fullName, password, "", "", 0L, phone, email)

    companion object {
        fun empty(): User {
            return User(
                "",
                "",
                "",
                "",
                0L,
                "",
                ""
            )
        }

        fun User.toUserRequest(longToken: Boolean = false): UserRequest {
            return UserRequest(
                UserName = this.fullName,
                PasswordHash = this.password.hashCode().toString(),
                LongToken = longToken
            )
        }
    }
}
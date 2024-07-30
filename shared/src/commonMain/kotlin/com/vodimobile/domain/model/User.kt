package com.vodimobile.domain.model

import com.vodimobile.domain.model.remote.dto.user_auth.UserRequest

data class User(
    val fullName: String,
    val password: String,
    val token: String,
    val phone: String,
    val email: String?
) {
    companion object {
        fun empty(): User {
            return User(
                "",
                "",
                "",
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
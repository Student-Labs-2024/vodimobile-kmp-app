package com.vodimobile.domain.model

data class User (
    val fullName: String,
    val password: String,
    val token : String,
    val phone: String,
    val email: String?
) {
    companion object{
        fun empty() : User {
            return User(
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}
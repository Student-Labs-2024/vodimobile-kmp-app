package com.vodimobile.presentation.utils.validator

class PasswordValidator {

    companion object {
        private const val REGEX_STRONG_PASSWORD =
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})"
    }

    fun isValidPassword(password: String): Boolean {
        return when {
            password.isNotEmpty() && REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(password) -> true
            else -> false
        }
    }
}

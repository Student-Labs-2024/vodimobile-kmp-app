package com.vodimobile.validator

import com.vodimobile.shared.resources.SharedRes
import dev.icerock.moko.resources.StringResource

class PasswordValidator {
    companion object {

        private const val REGEX_STRONG_PASSWORD =
            "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})"

        fun validate(password: String) : StringResource? {
            if (password.isEmpty())
                return SharedRes.strings.password_is_empty
            if (password.length < 8)
                return SharedRes.strings.password_must_be_longer
            if (!REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(password))
                return SharedRes.strings.password_must_contain_signs
            return null
        }
    }
}
package com.vodimobile.validator

import com.vodimobile.shared.resources.SharedRes
import dev.icerock.moko.resources.StringResource

class NameValidator {
    companion object {
        private const val REGEX_NAME = "([a-zA-Zа-яА-Я\\s]+)"

        fun validate(name: String) : StringResource? {
            if (name.isEmpty())
                return SharedRes.strings.name_is_empty
            if (!REGEX_NAME.toRegex().matches(name))
                return SharedRes.strings.name_contains_signs
            return null
        }
    }
}
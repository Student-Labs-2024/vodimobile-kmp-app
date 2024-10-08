package com.vodimobile.presentation.utils.validator

class NameValidator {

    companion object {
        private const val REGEX_NAME = "([a-zA-Zа-яА-Я\\s]+)"
    }

    fun isValidName(name: String): Boolean {
        return when {
            name.isNotEmpty() && REGEX_NAME.toRegex().matches(name) -> true
            else -> false
        }
    }
}

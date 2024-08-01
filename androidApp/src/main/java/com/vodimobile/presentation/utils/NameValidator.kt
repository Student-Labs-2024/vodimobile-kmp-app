package com.vodimobile.presentation.utils

class NameValidator {

    companion object {
        private const val REGEX_NAME = "([a-zA-Zа-яА-Я]+)"
    }

    fun isValidName(name: String): Boolean {
        return when {
            name.isNotEmpty() && REGEX_NAME.toRegex().matches(name) -> true
            else -> false
        }
    }
}

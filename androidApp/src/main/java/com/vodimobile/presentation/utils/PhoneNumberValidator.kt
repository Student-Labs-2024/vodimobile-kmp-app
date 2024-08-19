package com.vodimobile.presentation.utils

import android.util.Patterns

class PhoneNumberValidator {

    companion object {
        private const val RU_PHONE_NUMBER_LENGTH = 12
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty() &&
                phoneNumber.length == RU_PHONE_NUMBER_LENGTH &&
                Patterns.PHONE.matcher(phoneNumber).matches()
    }
}

package com.vodimobile.presentation.utils.validator

import android.util.Patterns

class PhoneNumberValidator {

    companion object {
        private const val RU_PHONE_NUMBER_LENGTH = 12
        private const val RU_CODE_COUNTRY = "+7"
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty() &&
                phoneNumber.length == RU_PHONE_NUMBER_LENGTH &&
                phoneNumber.startsWith(RU_CODE_COUNTRY) &&
                Patterns.PHONE.matcher(phoneNumber).matches()
    }
}

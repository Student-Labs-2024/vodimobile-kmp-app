package com.vodimobile.presentation.utils

import android.util.Patterns

class EmailValidator {

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

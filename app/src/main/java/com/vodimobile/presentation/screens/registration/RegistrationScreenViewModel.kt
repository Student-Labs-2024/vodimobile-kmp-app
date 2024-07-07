package com.vodimobile.presentation.screens.registration

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationScreenViewModel : ViewModel() {

    val registrationFieldsState = mutableStateOf(RegistrationFieldsState())

    fun onIntent(intent: RegistrationScreenIntent) {
        when (intent) {
            RegistrationScreenIntent.OpenUserAgreement -> {}
            RegistrationScreenIntent.SmsVerification -> {}
            RegistrationScreenIntent.ReturnBack -> {}

            is RegistrationScreenIntent.EmailChange -> {

                val isValidEmail = isValidEmail(intent.value)
                registrationFieldsState.value =
                    registrationFieldsState.value.copy(
                        email = intent.value,
                        emailError = !isValidEmail
                    )
            }

            is RegistrationScreenIntent.PhoneNumberChange -> {

                val isValidPhoneNumber = isValidPhoneNumber(intent.value)
                registrationFieldsState.value =
                    registrationFieldsState.value.copy(
                        phoneNumber = intent.value,
                        phoneNumberError = !isValidPhoneNumber
                    )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty() &&
                phoneNumber.length == 12 &&
                Patterns.PHONE.matcher(phoneNumber).matches()
    }
}

package com.vodimobile.presentation.screens.registration

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationScreenViewModel : ViewModel() {

    private val _registrationFieldsState = MutableStateFlow(RegistrationFieldsState())
    val registrationFieldsState: StateFlow<RegistrationFieldsState> = _registrationFieldsState

    fun onIntent(intent: RegistrationScreenIntent) {
        when (intent) {
            RegistrationScreenIntent.OpenUserAgreement -> {}
            RegistrationScreenIntent.SmsVerification -> {}
            RegistrationScreenIntent.ReturnBack -> {}

            is RegistrationScreenIntent.EmailChange -> {
                val isValidEmail = isValidEmail(intent.value)
                _registrationFieldsState.value = _registrationFieldsState.value.copy(
                    email = intent.value,
                    emailError = !isValidEmail
                )
            }

            is RegistrationScreenIntent.PhoneNumberChange -> {

                val isValidPhoneNumber = isValidPhoneNumber(intent.value)
                _registrationFieldsState.value = _registrationFieldsState.value.copy(
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

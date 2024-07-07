package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationScreenViewModel(
    private val emailValidator: EmailValidator,
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    private val _registrationFieldsState = MutableStateFlow(RegistrationFieldsState())
    val registrationFieldsState: StateFlow<RegistrationFieldsState> = _registrationFieldsState

    fun onIntent(intent: RegistrationScreenIntent) {
        when (intent) {
            RegistrationScreenIntent.OpenUserAgreement -> {}
            RegistrationScreenIntent.SmsVerification -> {}
            RegistrationScreenIntent.ReturnBack -> {}

            is RegistrationScreenIntent.EmailChange -> {
                val isValidEmail = validateEmail(intent.value)
                _registrationFieldsState.value = _registrationFieldsState.value.copy(
                    email = intent.value,
                    emailError = !isValidEmail
                )
            }

            is RegistrationScreenIntent.PhoneNumberChange -> {
                val isValidPhoneNumber = validatePhoneNumber(intent.value)
                _registrationFieldsState.value = _registrationFieldsState.value.copy(
                    phoneNumber = intent.value,
                    phoneNumberError = !isValidPhoneNumber
                )
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return emailValidator.isValidEmail(email)
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumberValidator.isValidPhoneNumber(phoneNumber)
    }
}

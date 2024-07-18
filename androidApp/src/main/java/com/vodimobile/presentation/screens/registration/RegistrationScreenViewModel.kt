package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.registration.store.RegistrationFieldsState
import com.vodimobile.presentation.screens.registration.store.RegistrationOutput
import com.vodimobile.presentation.screens.registration.store.RegistrationScreenIntent
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegistrationScreenViewModel(
    private val output: (RegistrationOutput) -> Unit,
) : ViewModel(), KoinComponent {

    private val _registrationFieldsState = MutableStateFlow(RegistrationFieldsState())
    val registrationFieldsState: StateFlow<RegistrationFieldsState> = _registrationFieldsState

    fun onIntent(intent: RegistrationScreenIntent) {
        when (intent) {
            RegistrationScreenIntent.OpenUserAgreement -> {
                onOutput(RegistrationOutput.OpenUserAgreement)
            }

            RegistrationScreenIntent.SmsVerification -> {
                onOutput(RegistrationOutput.SmsVerification)
            }

            RegistrationScreenIntent.ReturnBack -> {
                onOutput(RegistrationOutput.ReturnBack)
            }

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
        val emailValidator: EmailValidator by inject()
        return emailValidator.isValidEmail(email)
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberValidator: PhoneNumberValidator by inject()
        return phoneNumberValidator.isValidPhoneNumber(phoneNumber)
    }

    private fun onOutput(o: RegistrationOutput) {
        output(o)
    }
}

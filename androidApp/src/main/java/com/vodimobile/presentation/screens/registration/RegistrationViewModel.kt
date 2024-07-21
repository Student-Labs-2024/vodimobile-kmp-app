package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val emailValidator: EmailValidator,
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    val registrationState = MutableStateFlow(RegistrationState())
    val registrationEffect = MutableSharedFlow<RegistrationEffect>()

    fun onIntent(intent: RegistrationIntent) {
        when (intent) {
            RegistrationIntent.OpenUserAgreement -> {
                viewModelScope.launch {
                    registrationEffect.emit(RegistrationEffect.OpenUserAgreement)
                }
            }

            RegistrationIntent.SmsVerification -> {
                viewModelScope.launch {

                    registrationEffect.emit(RegistrationEffect.SmsVerification)
                }
            }

            RegistrationIntent.ReturnBack -> {
                viewModelScope.launch {
                    registrationEffect.emit(RegistrationEffect.ReturnBack)
                }
            }

            is RegistrationIntent.EmailChange -> {
                viewModelScope.launch {
                    val isValidEmail = validateEmail(intent.value)
                    registrationState.update {
                        it.copy(
                            email = intent.value,
                            emailError = !isValidEmail
                        )
                    }
                }
            }

            is RegistrationIntent.PhoneNumberChange -> {
                viewModelScope.launch {
                    val isValidPhoneNumber = validatePhoneNumber(intent.value)
                    registrationState.update {
                        it.copy(
                            phoneNumber = intent.value,
                            phoneNumberError = !isValidPhoneNumber
                        )
                    }
                }
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

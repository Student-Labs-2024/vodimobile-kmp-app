package com.vodimobile.presentation.screens.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.authorization.store.AuthorizationEffect
import com.vodimobile.presentation.screens.authorization.store.AuthorizationIntent
import com.vodimobile.presentation.screens.authorization.store.AuthorizationState
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    val authorizationState = MutableStateFlow(AuthorizationState())
    val authorizationEffect = MutableSharedFlow<AuthorizationEffect>()

    fun onIntent(intent: AuthorizationIntent) {
        when (intent) {
            AuthorizationIntent.OpenUserAgreement -> {
                viewModelScope.launch {
                    authorizationEffect.emit(AuthorizationEffect.OpenUserAgreement)
                }
            }

            AuthorizationIntent.SmsVerification -> {
                viewModelScope.launch {
                    authorizationEffect.emit(AuthorizationEffect.SmsVerification)
                }
            }

            AuthorizationIntent.ReturnBack -> {
                viewModelScope.launch {
                    authorizationEffect.emit(AuthorizationEffect.ReturnBack)
                }
            }

            is AuthorizationIntent.PhoneNumberChange -> {
                viewModelScope.launch {
                    val isValidPhoneNumber = validatePhoneNumber(intent.value)
                    authorizationState.update {
                        it.copy(
                            phoneNumber = intent.value,
                            phoneNumberError = !isValidPhoneNumber
                        )
                    }
                }
            }

            is AuthorizationIntent.PasswordChange -> {
                viewModelScope.launch {
                    val isValidPassword = validatePassword(intent.value)
                    authorizationState.update {
                        it.copy(
                            password = intent.value,
                            passwordError = !isValidPassword
                        )
                    }
                }
            }

            AuthorizationIntent.AskPermission -> {
                viewModelScope.launch {
                    authorizationEffect.emit(AuthorizationEffect.AskPermission)
                }
            }

            AuthorizationIntent.RememberPassword -> {
                viewModelScope.launch {
                    authorizationEffect.emit(AuthorizationEffect.RememberPassword)
                }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumberValidator.isValidPhoneNumber(phoneNumber) //add validate phone logic
    }

    private fun validatePassword(password: String): Boolean {
        return true  //add verification logic
    }
}

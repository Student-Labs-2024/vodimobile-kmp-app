package com.vodimobile.presentation.screens.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordEffect
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordIntent
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordState
import com.vodimobile.presentation.utils.validator.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val phoneNumberValidator: PhoneNumberValidator
) : ViewModel() {

    val resetPasswordState = MutableStateFlow(ResetPasswordState())
    val resetPasswordEffect = MutableSharedFlow<ResetPasswordEffect>()

    fun onIntent(intent: ResetPasswordIntent) {
        when (intent) {
            ResetPasswordIntent.SmsVerification -> {
                viewModelScope.launch {
                    resetPasswordEffect.emit(ResetPasswordEffect.SmsVerification)
                }
            }

            ResetPasswordIntent.ReturnBack -> {
                viewModelScope.launch {
                    resetPasswordEffect.emit(ResetPasswordEffect.ReturnBack)
                }
            }

            is ResetPasswordIntent.PhoneNumberChange -> {
                viewModelScope.launch {
                    val isValidPhoneNumber = validatePhoneNumber(intent.value)
                    resetPasswordState.update {
                        it.copy(
                            phoneNumber = intent.value,
                            phoneNumberError = !isValidPhoneNumber
                        )
                    }
                }
            }

            ResetPasswordIntent.AskPermission -> {
                viewModelScope.launch {
                    resetPasswordEffect.emit(ResetPasswordEffect.AskPermission)
                }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumberValidator.isValidPhoneNumber(phoneNumber)
    }
}

package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.utils.NameValidator
import com.vodimobile.presentation.utils.PasswordValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val phoneNumberValidator: PhoneNumberValidator,
    private val passwordValidator: PasswordValidator,
    private val nameValidator: NameValidator,
    private val dataStoreStorage: UserDataStoreStorage
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

            is RegistrationIntent.NameChanged -> {
                viewModelScope.launch {
                    val isValidName = validateName(intent.value)
                    registrationState.update {
                        it.copy(
                            name = intent.value,
                            nameError = !isValidName
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

            is RegistrationIntent.PasswordChange -> {
                viewModelScope.launch {
                    val isValidPassword = validatePassword(intent.value)
                    registrationState.update {
                        it.copy(
                            password = intent.value,
                            passwordError = !isValidPassword
                        )
                    }
                }
            }

            RegistrationIntent.AskPermission -> {
                viewModelScope.launch {
                    with(registrationState.value) {
                        dataStoreStorage.preregister(name = name, password = password, token = "")
                    }
                    registrationEffect.emit(RegistrationEffect.AskPermission)
                }
            }
        }
    }

    private fun validateName(name: String): Boolean {
        return nameValidator.isValidName(name)
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumberValidator.isValidPhoneNumber(phoneNumber)
    }

    private fun validatePassword(password: String): Boolean {
        return passwordValidator.isValidPassword(password)
    }
}

package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.utils.validator.NameValidator
import com.vodimobile.presentation.utils.validator.PasswordValidator
import com.vodimobile.presentation.utils.validator.PhoneNumberValidator
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val phoneNumberValidator: PhoneNumberValidator,
    private val passwordValidator: PasswordValidator,
    private val nameValidator: NameValidator,
    private val dataStoreStorage: UserDataStoreStorage,
    private val crmStorage: CrmStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {

    val registrationState = MutableStateFlow(RegistrationState())
    val registrationEffect = MutableSharedFlow<RegistrationEffect>()
    private val supervisorCoroutineContext = viewModelScope.coroutineContext + SupervisorJob()

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
                viewModelScope.launch(context = supervisorCoroutineContext) {

                    try {
                        val crmEither: CrmEither<UserResponse, HttpStatusCode> =
                            crmStorage.authUser()

                        when (crmEither) {
                            is CrmEither.CrmData -> {
                                with(crmEither.data) {
                                    saveInRemote(accessToken, refreshToken)
                                    saveInLocal()
                                }
                                registrationEffect.emit(RegistrationEffect.AskPermission)
                            }

                            is CrmEither.CrmError -> {
                                registrationEffect.emit(RegistrationEffect.SupabaseAuthUserError)
                            }

                            CrmEither.CrmLoading -> {

                            }
                        }

                    } catch (e: Exception) {
                        registrationEffect.emit(RegistrationEffect.NotUniquePhone)
                    }
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

    private suspend inline fun saveInLocal() {
        val user: User = supabaseStorage.getUser(
            password = registrationState.value.password,
            phone = registrationState.value.phoneNumber
        )
        dataStoreStorage.edit(user = user)
    }

    private suspend inline fun saveInRemote(accessToken: String, refreshToken: String) {
        with(registrationState.value) {
            try {
                supabaseStorage.insertUser(
                    user = User(
                        id = 0,
                        fullName = name,
                        password = password,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        phone = phoneNumber
                    )
                )
            } catch (e: Exception) {
                registrationEffect.emit(RegistrationEffect.SupabaseAuthUserError)
            }
        }
    }
}

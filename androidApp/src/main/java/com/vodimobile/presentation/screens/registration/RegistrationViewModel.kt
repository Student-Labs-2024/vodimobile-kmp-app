package com.vodimobile.presentation.screens.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.user_auth.UserResponse
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.repository.hash.HashRepository
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val phoneNumberValidator: PhoneNumberValidator,
    private val passwordValidator: PasswordValidator,
    private val nameValidator: NameValidator,
    private val dataStoreStorage: UserDataStoreStorage,
    private val crmStorage: CrmStorage,
    private val supabaseStorage: SupabaseStorage,
    private val hashRepository: HashRepository
) : ViewModel() {

    val registrationState = MutableStateFlow(RegistrationState())
    val registrationEffect = MutableSharedFlow<RegistrationEffect>()
    private val supervisorIOCoroutineContext = Dispatchers.IO + SupervisorJob()
    private var authJob: Job = Job()

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
                viewModelScope.launch(context = supervisorIOCoroutineContext) {
                    withContext(context = viewModelScope.coroutineContext) {
                        registrationEffect.emit(RegistrationEffect.ShowLoadingDialog)
                    }

                    viewModelScope.launch(context = supervisorIOCoroutineContext) {
                        val hasUserWithPhone: Boolean =
                            supabaseStorage.hasUserWithPhone(phone = registrationState.value.phoneNumber)

                        if (!hasUserWithPhone) {
                            try {
                                val crmEither: CrmEither<UserResponse, HttpStatusCode> =
                                    crmStorage.authUser()

                                when (crmEither) {
                                    is CrmEither.CrmData -> {
                                        with(crmEither.data) {
                                            with(registrationState.value) {
                                                val user = User(
                                                    id = 0,
                                                    fullName = name,
                                                    password = password,
                                                    accessToken = accessToken,
                                                    refreshToken = refreshToken,
                                                    phone = phoneNumber,
                                                )

                                                saveInRemote(
                                                    user = user,
                                                    accessToken = accessToken,
                                                    refreshToken = refreshToken
                                                )
                                            }
                                        }
                                        withContext(context = viewModelScope.coroutineContext) {
                                            registrationEffect.emit(RegistrationEffect.DismissLoadingDialog)
                                            registrationEffect.emit(RegistrationEffect.AskPermission)
                                        }
                                    }

                                    is CrmEither.CrmError -> {
                                        withContext(context = viewModelScope.coroutineContext) {
                                            registrationEffect.emit(RegistrationEffect.DismissLoadingDialog)
                                            registrationEffect.emit(RegistrationEffect.SupabaseAuthUserError)
                                        }
                                    }

                                    CrmEither.CrmLoading -> {

                                    }
                                }

                            } catch (e: Exception) {
                                withContext(context = viewModelScope.coroutineContext) {
                                    registrationEffect.emit(RegistrationEffect.DismissLoadingDialog)
                                    registrationEffect.emit(RegistrationEffect.NotUniquePhone)
                                }
                            }
                        } else {
                            withContext(context = viewModelScope.coroutineContext) {
                                registrationEffect.emit(RegistrationEffect.DismissLoadingDialog)
                                registrationEffect.emit(RegistrationEffect.SupabaseAuthUserError)
                            }
                        }
                    }
                }
            }

            RegistrationIntent.DismissAllCoroutines -> {
                authJob.cancel()
                viewModelScope.cancel()
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

    private suspend inline fun saveInLocal(user: User) {
        val userFromRemote: User = supabaseStorage.getUser(
            password = hashRepository.hash(text = user.password).decodeToString(),
            phone = user.phone
        )
        Log.d("TAG", userFromRemote.toString())
        dataStoreStorage.edit(user = userFromRemote)
    }

    private suspend fun saveInRemote(user: User, accessToken: String, refreshToken: String) {
        with(registrationState.value) {
            try {
                val hashedPassword = hashRepository.hash(text = user.password).decodeToString()

                supabaseStorage.insertUser(
                    user = User(
                        id = 0,
                        fullName = name,
                        password = hashedPassword,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        phone = phoneNumber
                    )
                )
                saveInLocal(user = user)
            } catch (e: Exception) {
                registrationEffect.emit(RegistrationEffect.SupabaseAuthUserError)
            }
        }
    }
}

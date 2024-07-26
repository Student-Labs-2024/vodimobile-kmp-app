package com.vodimobile.presentation.screens.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.change_password.store.ChangePasswordEffect
import com.vodimobile.presentation.screens.change_password.store.ChangePasswordIntent
import com.vodimobile.presentation.store.PasswordState
import com.vodimobile.presentation.utils.PasswordValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val passwordValidator: PasswordValidator) : ViewModel() {

    val oldPasswordState = MutableStateFlow(PasswordState())
    val newPasswordState = MutableStateFlow(PasswordState())

    val changePasswordEffect = MutableSharedFlow<ChangePasswordEffect>()

    fun onIntent(intent: ChangePasswordIntent) {
        when (intent) {
            ChangePasswordIntent.SaveChanges -> {
                viewModelScope.launch {
                    changePasswordEffect.emit(ChangePasswordEffect.SaveChanges)
                }
            }

            ChangePasswordIntent.ReturnBack -> {
                viewModelScope.launch {
                    changePasswordEffect.emit(ChangePasswordEffect.ReturnBack)
                }
            }

            is ChangePasswordIntent.OldPasswordChange -> {
                viewModelScope.launch {
                    val isValidPassword = validateOldPassword(intent.value)
                    oldPasswordState.update {
                        it.copy(
                            password = intent.value,
                            passwordError = !isValidPassword
                        )
                    }
                }
            }

            is ChangePasswordIntent.NewPasswordChange -> {
                viewModelScope.launch {
                    val isValidPassword = validateNewPassword(intent.value)
                    newPasswordState.update {
                        it.copy(
                            password = intent.value,
                            passwordError = !isValidPassword
                        )
                    }
                }
            }

            is ChangePasswordIntent.RememberPassword -> {
                viewModelScope.launch {
                    changePasswordEffect.emit(ChangePasswordEffect.RememberPassword)
                }
            }
        }
    }

    private fun validateOldPassword(password: String): Boolean {
        //change the logic when getting the api
        return password.isNotEmpty()
    }

    private fun validateNewPassword(password: String): Boolean {
        return passwordValidator.isValidPassword(password)
    }
}

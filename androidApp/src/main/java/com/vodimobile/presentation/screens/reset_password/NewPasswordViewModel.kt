package com.vodimobile.presentation.screens.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordEffect
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordIntent
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordState
import com.vodimobile.presentation.utils.validator.PasswordValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPasswordViewModel(
    private val passwordValidator: PasswordValidator,
    private val dataStoreStorage: UserDataStoreStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {

    val newPasswordState = MutableStateFlow(NewPasswordState())
    val newPasswordEffect = MutableSharedFlow<NewPasswordEffect>()

    fun onIntent(intent: NewPasswordIntent) {
        when (intent) {
            NewPasswordIntent.ReturnBack -> {
                viewModelScope.launch {
                    newPasswordEffect.emit(NewPasswordEffect.ReturnBack)
                }
            }

            is NewPasswordIntent.PasswordChange -> {
                viewModelScope.launch {
                    val isValidPassword = validatePassword(intent.value)
                    newPasswordState.update {
                        it.copy(
                            password = intent.value,
                            passwordError = !isValidPassword
                        )
                    }
                }
            }

            NewPasswordIntent.SaveData -> {
                viewModelScope.launch {
                    with(newPasswordState.value) {
                        dataStoreStorage.getUser().collect {
                            val remoteUser = supabaseStorage.getUser(password = it.password, phone = it.phone)
                            supabaseStorage.updatePassword(userId = remoteUser.id, password = newPasswordState.value.password)
                            dataStoreStorage.editPassword(password = newPasswordState.value.password)
                            newPasswordEffect.emit(NewPasswordEffect.SaveData)
                        }
                    }
                }
            }

            NewPasswordIntent.OpenUserAgreement -> {
                viewModelScope.launch {
                    newPasswordEffect.emit(NewPasswordEffect.OpenUserAgreement)
                }
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        return passwordValidator.isValidPassword(password)
    }
}

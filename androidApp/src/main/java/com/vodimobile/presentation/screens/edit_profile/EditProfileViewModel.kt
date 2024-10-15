package com.vodimobile.presentation.screens.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.android.R
import com.vodimobile.domain.model.User
import com.vodimobile.domain.repository.hash.HashRepository
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileEffect
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileIntent
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileState
import com.vodimobile.presentation.utils.validator.NameValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

class EditProfileViewModel(
    private val userDataStoreStorage: UserDataStoreStorage,
    private val supabaseStorage: SupabaseStorage,
    private val nameValidator: NameValidator,
    private val hashRepository: HashRepository
) : ViewModel() {

    val editProfileState = MutableStateFlow(EditProfileState())
    val editProfileEffect = MutableSharedFlow<EditProfileEffect>()

    init {
        val userStateFlow: Flow<User> = userDataStoreStorage.getUser()
        viewModelScope.launch {
            userStateFlow
                .catch {
                    //TODO() Catch error here
                }
                .collect { value: User ->
                    editProfileState.update {
                        it.copy(
                            user = value,
                            fullName = hashRepository.decrypt(text = value.fullName),
                            phone = hashRepository.decrypt(text = value.phone)
                        )
                    }
                }
        }
    }

    fun onIntent(intent: EditProfileIntent) {
        when (intent) {
            is EditProfileIntent.EditFullName -> {
                viewModelScope.launch {
                    val isValidName = validateName(intent.fullName)
                    editProfileState.update {
                        it.copy(
                            fullName = intent.fullName,
                            isFullNameError = !isValidName
                        )
                    }
                }
            }

            EditProfileIntent.EditPasswordClick -> {
                viewModelScope.launch {
                    editProfileEffect.emit(EditProfileEffect.ClickEditPassword)
                }
            }

            EditProfileIntent.ClickBack -> {
                viewModelScope.launch {
                    editProfileEffect.emit(EditProfileEffect.ClickBack)
                }
            }

            EditProfileIntent.SaveData -> {
                editProfileState.update {
                    it.copy(
                        isFullNameError = editProfileState.value.fullName.isEmpty()&&!validateName(name = editProfileState.value.fullName)
                    )
                }

                viewModelScope.launch {
                    val msg: Int
                    val action: Int

                    if (editProfileState.value.isFullNameError) {
                        msg = R.string.snackbar_error
                        action = R.string.snackbar_try_again

                        editProfileEffect.emit(
                            EditProfileEffect.SaveData(
                                msgResId = msg,
                                actionResId = action
                            )
                        )
                    } else {
                        msg = R.string.save_successfully
                        action = -1

                        editProfileEffect.emit(EditProfileEffect.ProgressDialog)
                        val user = supabaseStorage.getUser(
                            password = editProfileState.value.user.password,
                            phone = hashRepository.encrypt(text = editProfileState.value.user.phone)
                        )
                        userDataStoreStorage.edit(
                            user = User(
                                fullName = hashRepository.encrypt(text = editProfileState.value.fullName),
                                password = editProfileState.value.user.password,
                                phone = hashRepository.encrypt(text = editProfileState.value.user.phone),
                                accessToken = editProfileState.value.user.accessToken,
                                refreshToken = editProfileState.value.user.refreshToken,
                                id = user.id,
                            )
                        )
                        supabaseStorage.updateFullName(
                            userId = user.id,
                            fullName = hashRepository.encrypt(text = editProfileState.value.user.fullName)
                        )
                        editProfileEffect.emit(EditProfileEffect.RemoveProgressDialog)
                        editProfileEffect.emit(
                            EditProfileEffect.SaveData(
                                msgResId = msg,
                                actionResId = action
                            )
                        )
                    }
                }
            }
        }
    }
    private fun validateName(name: String): Boolean {
        return nameValidator.isValidName(name)
    }
}

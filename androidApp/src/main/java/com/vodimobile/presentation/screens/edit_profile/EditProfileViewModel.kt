package com.vodimobile.presentation.screens.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.android.R
import com.vodimobile.di.viewModelModule
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileEffect
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileIntent
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class EditProfileViewModel : ViewModel() {

    val editProfileState = MutableStateFlow(EditProfileState())
    val editProfileEffect = MutableSharedFlow<EditProfileEffect>()

    fun onIntent(intent: EditProfileIntent) {
        when (intent) {
            is EditProfileIntent.EditFullName -> {
                editProfileState.update {
                    it.copy(fullName = intent.fullName)
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
                        isFullNameError = editProfileState.value.fullName.isEmpty()
                    )
                }

                viewModelScope.launch {
                    editProfileEffect.emit(EditProfileEffect.ProgressDialog)
                    delay(5.seconds)
                    editProfileEffect.emit(EditProfileEffect.RemoveProgressDialog)

                    var msg: Int = 0
                    var action: Int? = 0

                    if (editProfileState.value.isFullNameError) {
                        msg = R.string.snackbar_error
                        action = R.string.snackbar_try_again
                    } else {
                        msg = R.string.save_successfully
                        action = -1
                    }

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
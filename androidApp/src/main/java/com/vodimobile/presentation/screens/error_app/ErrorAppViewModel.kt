package com.vodimobile.presentation.screens.error_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.contact.store.ContactEffect
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileEffect
import com.vodimobile.presentation.screens.error_app.store.ErrorAppEffect
import com.vodimobile.presentation.screens.error_app.store.ErrorAppIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ErrorAppViewModel : ViewModel() {
    val errorAppEffect = MutableSharedFlow<ErrorAppEffect>()

    fun onIntent(intent: ErrorAppIntent) {
        when (intent) {
            ErrorAppIntent.BackClick -> {
                viewModelScope.launch {
                    errorAppEffect.emit(ErrorAppEffect.BackClick)
                }
            }

            ErrorAppIntent.RepeatClick -> {
                viewModelScope.launch {
                    errorAppEffect.emit(ErrorAppEffect.ProgressDialog)
                    errorAppEffect.emit(ErrorAppEffect.RepeatClick)
                    errorAppEffect.emit(ErrorAppEffect.RemoveProgressDialog)
                }
            }
        }
    }
}

package com.vodimobile.presentation.screens.error_app.store

import com.vodimobile.presentation.screens.edit_profile.store.EditProfileEffect

sealed class ErrorAppEffect {
    data object BackClick : ErrorAppEffect()
    data object RepeatClick : ErrorAppEffect()
    data object ProgressDialog : ErrorAppEffect()
    data object RemoveProgressDialog : ErrorAppEffect()
}
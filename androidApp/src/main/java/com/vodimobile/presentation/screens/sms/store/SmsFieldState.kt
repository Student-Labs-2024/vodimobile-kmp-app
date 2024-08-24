package com.vodimobile.presentation.screens.sms.store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester

data class SmsFieldState(
    val focusRequester: FocusRequester,
    val text: MutableState<String> = mutableStateOf(""),
)

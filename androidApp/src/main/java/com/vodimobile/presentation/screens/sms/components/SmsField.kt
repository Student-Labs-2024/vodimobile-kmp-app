package com.vodimobile.presentation.screens.sms.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.vodimobile.presentation.screens.sms.store.SmsFieldState
import com.vodimobile.presentation.theme.ExtendedTheme

@Composable
fun RowScope.SmsField(
    state: SmsFieldState,
    onDone: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: Boolean = false,
    onValueChange: (String) -> Unit
) {
    val maxLength = 1
    val textFieldStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = ExtendedTheme.colorScheme.smsTextFieldBack,
        unfocusedContainerColor = ExtendedTheme.colorScheme.smsTextFieldBack,
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
        errorTextColor = MaterialTheme.colorScheme.onBackground
    )
    OutlinedTextField(
        value = state.text.value,
        onValueChange = { newText ->
            if (newText.length <= maxLength) state.text.value = newText
            onValueChange(state.text.value)
        },
        isError = error,
        textStyle = textFieldStyle,
        colors = textFieldColors,
        modifier = Modifier
            .wrapContentSize()
            .focusRequester(state.focusRequester)
            .weight(1.0f)
            .then(modifier),
        maxLines = 1,
        shape = MaterialTheme.shapes.small,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(state.text.value)
            }
        )
    )
}

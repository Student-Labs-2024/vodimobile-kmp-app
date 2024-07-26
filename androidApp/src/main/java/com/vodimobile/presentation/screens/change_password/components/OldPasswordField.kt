package com.vodimobile.presentation.screens.change_password.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun OldPasswordField(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    onClickRememberPassword: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            isError = isError,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val (icon, iconColor) = if (showPassword.value) {
                    Pair(
                        Icons.Filled.Visibility,
                        MaterialTheme.colorScheme.tertiary
                    )
                } else {
                    Pair(Icons.Filled.VisibilityOff, MaterialTheme.colorScheme.tertiary)
                }
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        icon,
                        contentDescription = stringResource(id = R.string.visibility_desc),
                        tint = iconColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                errorCursorColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                errorTextColor = MaterialTheme.colorScheme.onBackground
            ),
            shape = MaterialTheme.shapes.small
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (isError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onClickRememberPassword,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.remember_password_hint),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PasswordFieldLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        OldPasswordField(
            value = "password",
            label = stringResource(id = R.string.old_password_label),
            placeholder = stringResource(id = R.string.enter_password_placeholder),
            onValueChange = {},
            onClickRememberPassword = {},
            isError = false,
            errorMessage = stringResource(id = R.string.invalid_password)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordFieldNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        OldPasswordField(
            value = "password",
            label = stringResource(id = R.string.old_password_label),
            placeholder = stringResource(id = R.string.enter_password_placeholder),
            onValueChange = {},
            onClickRememberPassword = {},
            isError = false,
            errorMessage = stringResource(id = R.string.invalid_password)
        )
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PasswordFieldErrorLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        OldPasswordField(
            value = "password",
            label = stringResource(id = R.string.old_password_label),
            placeholder = stringResource(id = R.string.enter_password_placeholder),
            onValueChange = {},
            onClickRememberPassword = {},
            isError = true,
            errorMessage = stringResource(id = R.string.invalid_password)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordFieldErrorNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        OldPasswordField(
            value = "password",
            label = stringResource(id = R.string.old_password_label),
            placeholder = stringResource(id = R.string.enter_password_placeholder),
            onValueChange = {},
            onClickRememberPassword = {},
            isError = true,
            errorMessage = stringResource(id = R.string.invalid_password)
        )
    }
}

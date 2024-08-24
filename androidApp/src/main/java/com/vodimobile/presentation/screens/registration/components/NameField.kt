package com.vodimobile.presentation.screens.registration.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.android.R
import com.vodimobile.presentation.components.AuthenticationField
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun NameField(
    value: String,
    isError: Boolean,
    onNameChanged: (String) -> Unit
) {
    val maxNameLength = 100
    AuthenticationField(
        label = stringResource(id = R.string.label_name),
        value = value,
        onValueChange = onNameChanged,
        placeholder = stringResource(id = R.string.placeholder_name),
        keyboardType = KeyboardType.Text,
        isError = isError,
        errorMessage = when {
            value.isEmpty() -> stringResource(id = R.string.empty_name)
            value.length > maxNameLength -> stringResource(id = R.string.name_too_long)
            else -> stringResource(id = R.string.name_error)
        }
    )
}

@Preview
@Composable
private fun NameFieldPreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            NameField(
                value = "",
                isError = true,
                onNameChanged = {}
            )
        }
    }
}

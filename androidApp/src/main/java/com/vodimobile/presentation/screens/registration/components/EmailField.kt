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
fun EmailField(
    value: String,
    isError: Boolean,
    isShowError: Boolean,
    onEmailChanged: (String) -> Unit
) {
    AuthenticationField(
        label = stringResource(id = R.string.registration_label_email),
        value = value,
        onValueChange = onEmailChanged,
        placeholder = stringResource(id = R.string.placeholder_email),
        keyboardType = KeyboardType.Email,
        isError = isError && isShowError,
        errorMessage = stringResource(id = R.string.email_error)
    )
}

@Preview
@Composable
private fun EmailFieldPreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            EmailField(
                value = "",
                isError = true,
                isShowError = true,
                onEmailChanged = {}
            )
        }
    }
}

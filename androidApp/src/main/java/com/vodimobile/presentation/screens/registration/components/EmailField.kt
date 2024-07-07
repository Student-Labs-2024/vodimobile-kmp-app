package com.vodimobile.presentation.screens.registration.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.components.AuthenticationField
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.resources.Res
import com.vodimobile.resources.email_error
import com.vodimobile.resources.placeholder_email
import com.vodimobile.resources.registration_label_email
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailField(
    value: String,
    isError: Boolean,
    isShowError: Boolean,
    onEmailChanged: (String) -> Unit
) {
    AuthenticationField(
        label = stringResource(resource = Res.string.registration_label_email),
        value = value,
        onValueChange = onEmailChanged,
        placeholder = stringResource(resource = Res.string.placeholder_email),
        keyboardType = KeyboardType.Email,
        isError = isError && isShowError,
        errorMessage = stringResource(resource = Res.string.email_error)
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

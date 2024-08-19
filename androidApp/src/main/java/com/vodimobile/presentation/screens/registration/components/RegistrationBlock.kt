package com.vodimobile.presentation.screens.registration.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.PhoneField
import com.vodimobile.presentation.components.NewPasswordField
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun RegistrationBlock(
    registrationState: RegistrationState,
    isShowError: Boolean,
    onNameChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        NameField(
            value = registrationState.name,
            isError = registrationState.nameError && isShowError,
            onNameChanged = onNameChanged
        )
        PhoneField(
            value = registrationState.phoneNumber,
            isError = registrationState.phoneNumberError && isShowError,
            onPhoneNumberChanged = onPhoneNumberChanged
        )
        NewPasswordField(
            value = registrationState.password,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(
                id = R.string.create_password_placeholder
            ),
            onValueChange = onPasswordChange,
            isError = registrationState.passwordError && isShowError,
            errorMsg = stringResource(id = R.string.simple_password_error)
        )
    }
}

@Preview
@Composable
private fun RegistrationBlockPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            RegistrationBlock(
                registrationState = RegistrationState(),
                isShowError = false,
                onNameChanged = {},
                onPhoneNumberChanged = {},
                onPasswordChange = {}
            )
        }
    }
}

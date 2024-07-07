package com.vodimobile.presentation.screens.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.screens.registration.RegistrationFieldsState
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RegistrationBlock(
    registrationState: RegistrationFieldsState,
    isShowError: Boolean,
    onEmailChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        EmailField(
            value = registrationState.email,
            isError = registrationState.emailError,
            isShowError = isShowError,
            onEmailChanged = onEmailChanged,
        )
        PhoneField(
            value = registrationState.phoneNumber,
            isError = registrationState.phoneNumberError,
            isShowError = isShowError,
            onPhoneNumberChanged = onPhoneNumberChanged
        )
    }
}

@Preview
@Composable
fun RegistrationBlockPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            RegistrationBlock(
                registrationState = RegistrationFieldsState(),
                isShowError = false,
                onEmailChanged = {},
                onPhoneNumberChanged = {}
            )
        }
    }
}

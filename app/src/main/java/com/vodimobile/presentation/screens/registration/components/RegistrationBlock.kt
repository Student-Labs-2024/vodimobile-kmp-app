package com.vodimobile.presentation.screens.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RegistrationBlock() {

    var emailState by remember { mutableStateOf("") }
    var phoneNumberState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        EmailField(
            emailState = emailState,
            onEmailChanged = { emailState = it }
        )
        Spacer(modifier = Modifier.height(28.dp))
        PhoneField(
            phoneNumberState = phoneNumberState,
            onPhoneNumberChanged = { phoneNumberState = it }
        )
    }
}

@Preview
@Composable
fun RegistrationBlockPreview() {
    VodimobileTheme {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            RegistrationBlock()
        }
    }
}

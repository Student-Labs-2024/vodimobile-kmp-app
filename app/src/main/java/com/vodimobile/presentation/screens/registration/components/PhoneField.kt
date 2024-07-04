package com.vodimobile.presentation.screens.registration.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.R
import com.vodimobile.presentation.components.AuthenticationField
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.InputMasks
import com.vodimobile.presentation.utils.PhoneMaskVisualTransformation


@Composable
fun PhoneField(
    phoneNumberState: String,
    onPhoneNumberChanged: (String) -> Unit
) {
    AuthenticationField(
        label = stringResource(id = R.string.registration_label_phoneNumber),
        value = phoneNumberState,
        onValueChange = onPhoneNumberChanged,
        placeholder = stringResource(id = R.string.placeholder_phoneNumber),
        keyboardType = KeyboardType.Phone,
        clearIconContentDescription = stringResource(id = R.string.clear_phoneNumber_field_content_desc),
        maskVisualTransformation = PhoneMaskVisualTransformation(InputMasks.PHONE_MASK)
    )
}

@Preview
@Composable
fun PhoneFieldPreview() {

    VodimobileTheme {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            PhoneField(
                phoneNumberState = "",
                onPhoneNumberChanged = {}
            )
        }
    }
}

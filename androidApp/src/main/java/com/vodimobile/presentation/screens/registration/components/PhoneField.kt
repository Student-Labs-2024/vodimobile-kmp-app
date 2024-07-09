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
import com.vodimobile.presentation.utils.InputMasks
import com.vodimobile.presentation.utils.PhoneMaskVisualTransformation


@Composable
fun PhoneField(
    value: String,
    isError: Boolean,
    isShowError: Boolean,
    onPhoneNumberChanged: (String) -> Unit
) {
    AuthenticationField(
        label = stringResource(id = R.string.registration_label_phoneNumber),
        value = value,
        onValueChange = onPhoneNumberChanged,
        placeholder = stringResource(id = R.string.placeholder_phoneNumber),
        keyboardType = KeyboardType.Phone,
        maskVisualTransformation = PhoneMaskVisualTransformation(InputMasks.RU_PHONE_MASK),
        isError = isError && isShowError,
        errorMessage = stringResource(id = R.string.phoneNumber_error),
    )
}

@Preview
@Composable
private fun PhoneFieldPreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            PhoneField(
                value = "",
                isError = true,
                isShowError = true,
                onPhoneNumberChanged = {}
            )
        }
    }
}

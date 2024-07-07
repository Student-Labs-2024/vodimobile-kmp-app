package com.vodimobile.presentation.screens.registration.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.components.AuthenticationField
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.InputMasks
import com.vodimobile.presentation.utils.PhoneMaskVisualTransformation
import com.vodimobile.resources.Res
import com.vodimobile.resources.phoneNumber_error
import com.vodimobile.resources.placeholder_phoneNumber
import com.vodimobile.resources.registration_label_phoneNumber
import org.jetbrains.compose.resources.stringResource


@Composable
fun PhoneField(
    value: String,
    isError: Boolean,
    isShowError: Boolean,
    onPhoneNumberChanged: (String) -> Unit
) {
    AuthenticationField(
        label = stringResource(resource = Res.string.registration_label_phoneNumber),
        value = value,
        onValueChange = onPhoneNumberChanged,
        placeholder = stringResource(resource = Res.string.placeholder_phoneNumber),
        keyboardType = KeyboardType.Phone,
        maskVisualTransformation = PhoneMaskVisualTransformation(InputMasks.RU_PHONE_MASK),
        isError = isError && isShowError,
        errorMessage = stringResource(resource = Res.string.phoneNumber_error),
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

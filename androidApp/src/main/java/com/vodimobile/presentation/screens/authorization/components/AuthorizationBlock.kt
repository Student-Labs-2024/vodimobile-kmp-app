package com.vodimobile.presentation.screens.authorization.components

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import com.vodimobile.presentation.screens.authorization.store.AuthorizationState
import com.vodimobile.presentation.components.OldPasswordField
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun AuthorizationBlock(
    authorizationState: AuthorizationState,
    isShowError: Boolean,
    errorMsg: Int,
    onPhoneNumberChanged: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickRememberPassword: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        PhoneField(
            value = authorizationState.phoneNumber,
            isError = authorizationState.phoneNumberError && isShowError,
            onPhoneNumberChanged = onPhoneNumberChanged
        )
        OldPasswordField(
            value = authorizationState.password,
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(
                id = R.string.enter_password_placeholder
            ),
            onValueChange = onPasswordChange,
            isError = authorizationState.passwordError && isShowError,
            errorMessage =
            if (authorizationState.password.isEmpty())
                stringResource(id = R.string.empty_password) else stringResource(id = errorMsg),
            onClickRememberPassword = onClickRememberPassword
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthorizationBlockLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            AuthorizationBlock(
                authorizationState = AuthorizationState(),
                isShowError = false,
                errorMsg = R.string.invalid_password,
                onPhoneNumberChanged = {},
                onPasswordChange = {},
                onClickRememberPassword = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthorizationBlockNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            AuthorizationBlock(
                authorizationState = AuthorizationState(),
                isShowError = false,
                errorMsg = R.string.invalid_password,
                onPhoneNumberChanged = {},
                onPasswordChange = {},
                onClickRememberPassword = {}
            )
        }
    }
}

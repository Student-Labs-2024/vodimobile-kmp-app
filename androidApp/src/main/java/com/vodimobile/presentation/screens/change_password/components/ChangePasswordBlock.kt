package com.vodimobile.presentation.screens.change_password.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.NewPasswordField
import com.vodimobile.presentation.components.OldPasswordField
import com.vodimobile.presentation.screens.change_password.store.PasswordState
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun ChangePasswordBlock(
    oldPasswordState: PasswordState,
    newPasswordState: PasswordState,
    onOldPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onClickRememberPassword: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OldPasswordField(
            value = oldPasswordState.password,
            label = stringResource(id = R.string.old_password_label),
            placeholder = stringResource(id = R.string.enter_password_placeholder),
            onValueChange = onOldPasswordChanged,
            isError = oldPasswordState.passwordError,
            errorMessage = stringResource(id = R.string.invalid_password),
            onClickRememberPassword = onClickRememberPassword
        )
        NewPasswordField(
            value = newPasswordState.password,
            label = stringResource(id = R.string.new_password_label),
            placeholder = stringResource(id = R.string.create_password_placeholder),
            onValueChange = onNewPasswordChanged,
            isError = newPasswordState.passwordError,
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
            ChangePasswordBlock(
                oldPasswordState = PasswordState(),
                newPasswordState = PasswordState(),
                onClickRememberPassword = {},
                onNewPasswordChanged = {},
                onOldPasswordChanged = {}
            )
        }
    }
}

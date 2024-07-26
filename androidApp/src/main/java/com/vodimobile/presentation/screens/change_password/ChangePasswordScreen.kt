package com.vodimobile.presentation.screens.change_password

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.change_password.components.ChangePasswordBlock
import com.vodimobile.presentation.screens.change_password.store.ChangePasswordEffect
import com.vodimobile.presentation.screens.change_password.store.ChangePasswordIntent
import com.vodimobile.presentation.store.PasswordState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.PasswordValidator
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ChangePasswordScreen(
    onChangePasswordIntent: (ChangePasswordIntent) -> Unit,
    oldPasswordState: State<PasswordState>,
    newPasswordState: State<PasswordState>,
    @SuppressLint("ComposeMutableParameters") changePasswordEffect: MutableSharedFlow<ChangePasswordEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        changePasswordEffect.collect { effect ->
            when (effect) {
                ChangePasswordEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                ChangePasswordEffect.SaveChanges -> {
                    navHostController.navigateUp()
                }

                ChangePasswordEffect.RememberPassword -> {

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 16.dp)
    ) {
        ScreenHeader(
            title = stringResource(
                id = R.string.title_change_password_screen
            ),
            onNavigateBack = {
                onChangePasswordIntent(ChangePasswordIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        ChangePasswordBlock(
            oldPasswordState = oldPasswordState.value,
            newPasswordState = newPasswordState.value,
            onOldPasswordChanged = {
                onChangePasswordIntent(ChangePasswordIntent.OldPasswordChange(it))
            },
            onNewPasswordChanged = {
                onChangePasswordIntent(ChangePasswordIntent.NewPasswordChange(it))
            },
            onClickRememberPassword = {
                onChangePasswordIntent(ChangePasswordIntent.RememberPassword)
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        PrimaryButton(
            text = stringResource(id = R.string.text_save_button),
            enabled = oldPasswordState.value.password.isNotEmpty() &&
                    newPasswordState.value.password.isNotEmpty() &&
                    !oldPasswordState.value.passwordError &&
                    !newPasswordState.value.passwordError,
            onClick = {
                onChangePasswordIntent(ChangePasswordIntent.SaveChanges)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ChangePasswordScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val changePasswordViewModel = ChangePasswordViewModel(
                passwordValidator = PasswordValidator()
            )
            ChangePasswordScreen(
                onChangePasswordIntent = changePasswordViewModel::onIntent,
                oldPasswordState = changePasswordViewModel.oldPasswordState.collectAsState(),
                newPasswordState = changePasswordViewModel.newPasswordState.collectAsState(),
                changePasswordEffect = changePasswordViewModel.changePasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChangePasswordScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val changePasswordViewModel = ChangePasswordViewModel(
                passwordValidator = PasswordValidator()
            )
            ChangePasswordScreen(
                onChangePasswordIntent = changePasswordViewModel::onIntent,
                oldPasswordState = changePasswordViewModel.oldPasswordState.collectAsState(),
                newPasswordState = changePasswordViewModel.newPasswordState.collectAsState(),
                changePasswordEffect = changePasswordViewModel.changePasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

package com.vodimobile.presentation.screens.reset_password

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.App
import com.vodimobile.android.R
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.components.PhoneField
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordEffect
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordIntent
import com.vodimobile.presentation.screens.reset_password.store.ResetPasswordState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ResetPasswordScreen(
    onResetPasswordIntent: (ResetPasswordIntent) -> Unit,
    resetPasswordState: State<ResetPasswordState>,
    @SuppressLint("ComposeMutableParameters") passwordResetEffect: MutableSharedFlow<ResetPasswordEffect>,
    navHostController: NavHostController
) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onResetPasswordIntent(ResetPasswordIntent.SmsVerification)
        }
    }

    LaunchedEffect(key1 = Unit) {
        passwordResetEffect.collect { effect ->
            when (effect) {
                ResetPasswordEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                ResetPasswordEffect.SmsVerification -> {
                    navHostController.navigate(
                        route = "${RegistrationScreens.SMS_VERIFY}/" +
                                "${resetPasswordState.value.phoneNumber}/" +
                                RegistrationScreens.NEW_PASSWORD_SCREEN
                    )
                }

                ResetPasswordEffect.AskPermission -> {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            App.INSTANCE,
                            android.Manifest.permission.SEND_SMS
                        ) -> {
                            onResetPasswordIntent(ResetPasswordIntent.SmsVerification)
                        }

                        else -> {
                            launcher.launch(android.Manifest.permission.SEND_SMS)
                        }
                    }
                }
            }
        }
    }

    val isButtonClicked = remember { mutableStateOf(false) }

    fun resetButtonClicked() {
        if (isButtonClicked.value) isButtonClicked.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = stringResource(
                id = R.string.header_reset_password
            ),
            onNavigateBack = {
                onResetPasswordIntent(ResetPasswordIntent.ReturnBack)
            }
        )
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title_reset_password),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = R.string.subtitle_reset_password),
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
        PhoneField(
            value = resetPasswordState.value.phoneNumber,
            isError = resetPasswordState.value.phoneNumberError && isButtonClicked.value,
            onPhoneNumberChanged = {
                onResetPasswordIntent(ResetPasswordIntent.PhoneNumberChange(it))
                resetButtonClicked()
            }
        )
        PrimaryButton(
            text = stringResource(id = R.string.text_next_button),
            enabled = true,
            onClick = {
                isButtonClicked.value = true
                if (!resetPasswordState.value.phoneNumberError)
                    onResetPasswordIntent(ResetPasswordIntent.AskPermission)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ResetPasswordScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val resetPasswordViewModel = ResetPasswordViewModel(
                phoneNumberValidator = PhoneNumberValidator()
            )
            ResetPasswordScreen(
                onResetPasswordIntent = resetPasswordViewModel::onIntent,
                resetPasswordState = resetPasswordViewModel.resetPasswordState.collectAsState(),
                passwordResetEffect = resetPasswordViewModel.resetPasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ResetPasswordScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val resetPasswordViewModel = ResetPasswordViewModel(
                phoneNumberValidator = PhoneNumberValidator()
            )
            ResetPasswordScreen(
                onResetPasswordIntent = resetPasswordViewModel::onIntent,
                resetPasswordState = resetPasswordViewModel.resetPasswordState.collectAsState(),
                passwordResetEffect = resetPasswordViewModel.resetPasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

package com.vodimobile.presentation.screens.registration

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.screens.registration.components.AgreementBlock
import com.vodimobile.presentation.screens.registration.components.RegistrationBlock
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun RegistrationScreen(
    onRegistrationIntent: (RegistrationIntent) -> Unit,
    registrationState: State<RegistrationState>,
    registrationEffect: MutableSharedFlow<RegistrationEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        registrationEffect.collect { effect ->
            when (effect) {
                RegistrationEffect.OpenUserAgreement -> {
                    navHostController.navigate(RegistrationScreens.USER_AGREE_SCREEN)
                }

                RegistrationEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                RegistrationEffect.SmsVerification -> {

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
            .padding(vertical = 64.dp, horizontal = 16.dp)
    ) {
        ScreenHeader(
            title = stringResource(
                id = R.string.title_screen_registration
            ),
            onNavigateBack = {
                onRegistrationIntent(RegistrationIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        RegistrationBlock(
            registrationState = registrationState.value,
            isShowError = isButtonClicked.value,
            onEmailChanged = {
                onRegistrationIntent(RegistrationIntent.EmailChange(it))
                resetButtonClicked()
            },
            onPhoneNumberChanged = {
                onRegistrationIntent(RegistrationIntent.PhoneNumberChange(it))
                resetButtonClicked()
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        AgreementBlock(
            onClickUserAgreement = {
                onRegistrationIntent(RegistrationIntent.OpenUserAgreement)
            },
            onClickNextButton = {
                isButtonClicked.value = true
                if (!registrationState.value.emailError && !registrationState.value.phoneNumberError)
                    onRegistrationIntent(RegistrationIntent.SmsVerification)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegistrationScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val registrationViewModel = RegistrationViewModel(
                emailValidator = EmailValidator(),
                phoneNumberValidator = PhoneNumberValidator()
            )
            RegistrationScreen(
                onRegistrationIntent = registrationViewModel::onIntent,
                registrationState = registrationViewModel.registrationState.collectAsState(),
                registrationEffect = registrationViewModel.registrationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RegistrationScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val registrationViewModel = RegistrationViewModel(
                emailValidator = EmailValidator(),
                phoneNumberValidator = PhoneNumberValidator()
            )
            RegistrationScreen(
                onRegistrationIntent = registrationViewModel::onIntent,
                registrationState = registrationViewModel.registrationState.collectAsState(),
                registrationEffect = registrationViewModel.registrationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

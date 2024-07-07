package com.vodimobile.presentation.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.screens.registration.components.AgreementBlock
import com.vodimobile.presentation.screens.registration.components.RegistrationBlock
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import com.vodimobile.resources.Res
import com.vodimobile.resources.title_screen_registration
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegistrationScreen(viewModel: RegistrationScreenViewModel) {

    val registrationState = viewModel.registrationFieldsState.collectAsState()
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
                resource = Res.string.title_screen_registration
            ),
            onNavigateBack = {
                viewModel.onIntent(RegistrationScreenIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        RegistrationBlock(
            registrationState = registrationState.value,
            isShowError = isButtonClicked.value,
            onEmailChanged = {
                viewModel.onIntent(RegistrationScreenIntent.EmailChange(it))
                resetButtonClicked()
            },
            onPhoneNumberChanged = {
                viewModel.onIntent(RegistrationScreenIntent.PhoneNumberChange(it))
                resetButtonClicked()
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        AgreementBlock(
            onClickUserAgreement = {
                viewModel.onIntent(RegistrationScreenIntent.OpenUserAgreement)

            },
            onClickNextButton = {
                isButtonClicked.value = true
                if (!registrationState.value.emailError && !registrationState.value.phoneNumberError)
                    viewModel.onIntent(RegistrationScreenIntent.SmsVerification)
            }
        )
    }
}

@Preview
@Composable
private fun RegistrationScreenPreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            RegistrationScreen(
                viewModel = RegistrationScreenViewModel(
                    emailValidator = EmailValidator(),
                    phoneNumberValidator = PhoneNumberValidator()
                )
            )
        }
    }
}

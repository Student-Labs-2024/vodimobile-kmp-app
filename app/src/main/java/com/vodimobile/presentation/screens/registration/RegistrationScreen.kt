package com.vodimobile.presentation.screens.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.screens.registration.components.AgreementBlock
import com.vodimobile.presentation.screens.registration.components.RegistrationBlock
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RegistrationScreen(viewModel: RegistrationScreenViewModel) {

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
                viewModel.onIntent(RegistrationScreenIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        RegistrationBlock()
        Spacer(modifier = Modifier.height(28.dp))
        AgreementBlock(
            onClickUserAgreement = {
                viewModel.onIntent(RegistrationScreenIntent.OpenUserAgreement)
            },
            onClickNextButton = {
                viewModel.onIntent(RegistrationScreenIntent.SmsVerification)
            }
        )
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {

    VodimobileTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            RegistrationScreen(
                viewModel = RegistrationScreenViewModel()
            )
        }
    }
}

package com.vodimobile.presentation.screens.user_agreement

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserAgreementScreen(viewModel: UserAgreementViewModel) {

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(
                    id = R.string.title_user_agreement
                ),
                onNavigateBack = {
                    viewModel.onIntent(UserAgreementScreenIntent.ReturnBack)
                },
            )
        }
    ) { innerPadding ->
        Text(
            text = stringResource(id = R.string.text_user_agreement),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            softWrap = true,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview
@Composable
private fun UserAgreementScreenPreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            UserAgreementScreen(viewModel = UserAgreementViewModel())
        }
    }
}

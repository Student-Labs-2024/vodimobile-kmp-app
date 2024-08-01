package com.vodimobile.presentation.screens.user_agreement

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementEffect
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ComposeModifierMissing")
@Composable
fun UserAgreementScreen(
    onUserAgreementIntent: (UserAgreementIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") userAgreementEffect: MutableSharedFlow<UserAgreementEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        userAgreementEffect.collect { effect ->
            when (effect) {
                UserAgreementEffect.ClickBack -> navHostController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(
                    id = R.string.title_user_agreement
                ),
                onNavigateBack = {
                    onUserAgreementIntent(UserAgreementIntent.ReturnBack)
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UserAgreementScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val userAgreementViewModel = UserAgreementViewModel()
            UserAgreementScreen(
                onUserAgreementIntent = userAgreementViewModel::onIntent,
                userAgreementEffect = userAgreementViewModel.userAgreementEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun UserAgreementScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val userAgreementViewModel = UserAgreementViewModel()
            UserAgreementScreen(
                onUserAgreementIntent = userAgreementViewModel::onIntent,
                userAgreementEffect = userAgreementViewModel.userAgreementEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

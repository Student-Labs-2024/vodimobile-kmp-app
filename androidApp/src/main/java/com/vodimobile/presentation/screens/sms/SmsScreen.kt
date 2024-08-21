package com.vodimobile.presentation.screens.sms

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
//import com.google.android.gms.auth.api.identity.Identity
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.sms.components.SendCodeAgain
import com.vodimobile.presentation.screens.sms.components.SmsField
import com.vodimobile.presentation.screens.sms.components.SmsHeadLine
import com.vodimobile.presentation.screens.sms.store.SmsEffect
import com.vodimobile.presentation.screens.sms.store.SmsIntent
import com.vodimobile.presentation.screens.sms.store.SmsState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun SmsScreen(
    smsState: State<SmsState>,
    @SuppressLint("ComposeMutableParameters") smsEffect: MutableSharedFlow<SmsEffect>,
    phone: String,
    navigateScreen: String,
    onIntent: (SmsIntent) -> Unit,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val focus = remember { FocusRequester() }
    val smsFields = remember {
        mutableStateListOf(
            SmsFieldState(focusRequester = focus),
            SmsFieldState(focusRequester = focus),
            SmsFieldState(focusRequester = focus),
            SmsFieldState(focusRequester = focus),
        )
    }

    val isAllFieldsFilled = remember {
        derivedStateOf {
            smsFields.all { it.text.value.isNotBlank() }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onIntent(SmsIntent.SendSmsCode(phone = phone, context = context))
    }

    LaunchedEffect(key1 = Unit) {
        smsEffect.collect { effect ->
            when (effect) {
                SmsEffect.SmsCodeCorrect -> {
                    navHostController.navigate(navigateScreen)
                }
            }
        }
    }

    ExtendedTheme {
        Scaffold(topBar = {
            ScreenHeader(
                title = stringResource(id = R.string.sms_title),
                onNavigateBack = { navHostController.popBackStack() }
            )
        }) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                SmsHeadLine(phone = phone)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .padding(horizontal = 44.dp, vertical = 22.dp)
                        .wrapContentSize()
                ) {
                    smsFields.forEachIndexed { index, fieldState ->
                        SmsField(
                            state = fieldState,
                            error = !smsState.value.isIncorrectCode,
                            onValueChange = { partCode ->
                                var part = 0
                                try {
                                    part = Integer.parseInt(
                                        partCode
                                    )
                                } catch (_: NumberFormatException) {

                                }

                                onIntent(
                                    SmsIntent.OnInputPartCode(
                                        partCode = part,
                                        index = index
                                    )
                                )
                            },
                            onDone = { partCode ->
                                if (index < smsFields.size - 1) {
                                    smsFields[index + 1].focusRequester.requestFocus()
                                }

                                var part = 0
                                try {
                                    part = Integer.parseInt(
                                        partCode
                                    )
                                } catch (_: NumberFormatException) {

                                }

                                onIntent(
                                    SmsIntent.OnInputPartCode(
                                        partCode = part,
                                        index = index
                                    )
                                )
                            }
                        )
                    }
                }

                if (!smsState.value.isIncorrectCode) {
                    Text(
                        text = stringResource(id = R.string.incorrect_code),
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error)
                    )
                }

                SendCodeAgain(
                    onIntent = onIntent,
                    isAllFieldsFilled = isAllFieldsFilled.value,
                    countDown = smsState.value.countDownAgain
                )
            }
        }
    }
}

data class SmsFieldState(
    val focusRequester: FocusRequester,
    val text: MutableState<String> = mutableStateOf(""),
)

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SmsScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        val smsViewModel = SmsViewModel()
        SmsScreen(
            navHostController = rememberNavController(),
            smsState = smsViewModel.smsState.collectAsState(),
            smsEffect = smsViewModel.smsEffect,
            phone = "8 800 555 35 35",
            navigateScreen = "",
            onIntent = smsViewModel::onIntent
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SmsScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        val smsViewModel = SmsViewModel()
        SmsScreen(
            navHostController = rememberNavController(),
            smsState = smsViewModel.smsState.collectAsState(),
            smsEffect = smsViewModel.smsEffect,
            phone = "8 800 555 35 35",
            navigateScreen = "",
            onIntent = smsViewModel::onIntent
        )
    }
}

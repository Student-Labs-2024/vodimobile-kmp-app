package com.vodimobile.presentation.screens.sms.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.user_actions.PrimaryButton
import com.vodimobile.presentation.screens.sms.store.SmsIntent
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun SendCodeAgain(onIntent: (SmsIntent) -> Unit, isAllFieldsFilled: Boolean, countDown: Int) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.confirm),
            enabled = isAllFieldsFilled,
            onClick = {
                onIntent(SmsIntent.OnConfirmSmsCode)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                text = stringResource(id = R.string.not_get_code),
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(
                onClick = {
                    onIntent(SmsIntent.SendSmsCodeAgain)
                }
            ) {
                Text(
                    text = if (countDown == 0) stringResource(id = R.string.send_again) else stringResource(
                        id = R.string.send_code_in, countDown
                    )
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SendCodeAgainDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        ExtendedTheme {
            Scaffold {
                SendCodeAgain({}, isAllFieldsFilled = false, 0)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SendCodeAgainLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        ExtendedTheme {
            Scaffold {
                SendCodeAgain({}, isAllFieldsFilled = true, 25)
            }
        }
    }
}

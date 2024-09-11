package com.vodimobile.presentation.screens.sms.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun SmsHeadLine(phone: String) {
    Text(
        text = stringResource(id = R.string.enter_sms_code),
        style = MaterialTheme.typography.headlineLarge
    )
    Text(
        text = stringResource(id = R.string.we_send_sms_code, phone),
        style = MaterialTheme.typography.bodyMedium.copy(color = ExtendedTheme.colorScheme.hintText)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "RU"
)
private fun SmsHeadLineDarkRuPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            SmsHeadLine("23456789")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "EN"
)
private fun SmsHeadLineLightEnPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            SmsHeadLine("3254678976")
        }
    }
}

package com.vodimobile.presentation.screens.sms.store

import android.content.Context

sealed class SmsIntent {
    data object OnConfirmSmsCode : SmsIntent()
    data object SendSmsCodeAgain : SmsIntent()
    data class SendSmsCode(val phone: String, val context: Context) : SmsIntent()
}
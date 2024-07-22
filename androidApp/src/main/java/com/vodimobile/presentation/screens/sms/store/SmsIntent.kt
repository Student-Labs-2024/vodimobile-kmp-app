package com.vodimobile.presentation.screens.sms.store

sealed class SmsIntent {
    data object OnConfirmSmsCode : SmsIntent()
    data object SendSmsCodeAgain : SmsIntent()
}
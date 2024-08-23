package com.vodimobile.presentation.screens.sms.store

sealed class SmsEffect {
    data object SmsCodeCorrect : SmsEffect()
}

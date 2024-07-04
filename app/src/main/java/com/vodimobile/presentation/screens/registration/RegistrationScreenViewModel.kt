package com.vodimobile.presentation.screens.registration

import androidx.lifecycle.ViewModel

class RegistrationScreenViewModel : ViewModel() {

    fun onIntent(intent: RegistrationScreenIntent) {
        when (intent) {
            RegistrationScreenIntent.OpenUserAgreement -> {}
            RegistrationScreenIntent.SmsVerification -> {}
            RegistrationScreenIntent.ReturnBack -> {}
        }
    }
}

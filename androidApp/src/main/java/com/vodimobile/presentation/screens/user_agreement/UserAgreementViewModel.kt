package com.vodimobile.presentation.screens.user_agreement

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementOutput
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementScreenIntent

class UserAgreementViewModel(private val output: (UserAgreementOutput) -> Unit) : ViewModel() {

    fun onIntent(intent: UserAgreementScreenIntent) {
        when (intent) {
            UserAgreementScreenIntent.ReturnBack -> {
                onOutput(UserAgreementOutput.ReturnBack)
            }
        }
    }

    private fun onOutput(o: UserAgreementOutput) {
        output(o)
    }
}

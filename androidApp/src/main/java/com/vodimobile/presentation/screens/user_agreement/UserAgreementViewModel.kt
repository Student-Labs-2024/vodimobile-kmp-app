package com.vodimobile.presentation.screens.user_agreement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementEffect
import com.vodimobile.presentation.screens.user_agreement.store.UserAgreementIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class UserAgreementViewModel : ViewModel() {

    val userAgreementEffect = MutableSharedFlow<UserAgreementEffect>()

    fun onIntent(intent: UserAgreementIntent) {
        when (intent) {
            UserAgreementIntent.ReturnBack -> {
                viewModelScope.launch {
                    userAgreementEffect.emit(UserAgreementEffect.ClickBack)
                }
            }
        }
    }
}

package com.vodimobile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.profile.store.ProfileIntent

class ProfileViewModel : ViewModel() {

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.appExitClick -> {}
            ProfileIntent.constantsClick -> {}
            ProfileIntent.faqClick -> {}
            ProfileIntent.personalDataClick -> {}
            ProfileIntent.rulesClick -> {}
        }
    }
}
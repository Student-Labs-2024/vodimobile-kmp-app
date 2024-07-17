package com.vodimobile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import com.vodimobile.presentation.screens.profile.store.ProfileOutput

class ProfileViewModel(private val output: (ProfileOutput) -> Unit) : ViewModel() {

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.AppExitClick -> {
                onOutput(ProfileOutput.AppExitClick)
            }
            ProfileIntent.ConstantsClick -> {
                onOutput(ProfileOutput.ConstantsClick)
            }
            ProfileIntent.FaqClick -> {
                onOutput(ProfileOutput.FaqClick)
            }
            ProfileIntent.PersonalDataClick -> {
                onOutput(ProfileOutput.PersonalDataClick)
            }
            ProfileIntent.RulesClick -> {
                onOutput(ProfileOutput.RulesClick)
            }
        }
    }

    private fun onOutput(o: ProfileOutput) {
        output(o)
    }
}
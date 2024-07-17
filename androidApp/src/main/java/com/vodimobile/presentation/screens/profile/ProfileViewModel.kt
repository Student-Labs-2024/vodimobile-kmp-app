package com.vodimobile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.screens.profile.store.ProfileIntent

class ProfileViewModel(private val navController: NavHostController) : ViewModel() {

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.AppExitClick -> {
                navController.navigate(DialogIdentifiers.LOG_OUT_DIALOG)
            }

            ProfileIntent.ConstantsClick -> {}
            ProfileIntent.FaqClick -> {}
            ProfileIntent.PersonalDataClick -> {}
            ProfileIntent.RulesClick -> {
                navController.navigate(LeafScreen.RULES_SCREEN)
            }
        }
    }
}

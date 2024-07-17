package com.vodimobile.presentation.screens.startscreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class StartScreenViewModel(private val navController: NavHostController) : ViewModel() {

    fun onIntent(intent: Intent) {
        when (intent) {
            Intent.ClickLogin -> {}
            Intent.ClickRegistration -> {}
            Intent.CloseClick -> {}
        }
    }
}

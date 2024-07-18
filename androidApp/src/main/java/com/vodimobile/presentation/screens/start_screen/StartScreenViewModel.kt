package com.vodimobile.presentation.screens.start_screen

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.start_screen.store.StartScreenIntent
import com.vodimobile.presentation.screens.start_screen.store.StartScreenOutput

class StartScreenViewModel(private val output: (StartScreenOutput) -> Unit) : ViewModel() {

    fun onIntent(startScreenIntent: StartScreenIntent) {
        when (startScreenIntent) {
            StartScreenIntent.ClickLogin -> {
                onOutput(StartScreenOutput.ClickLogin)
            }
            StartScreenIntent.ClickRegistration -> {
                onOutput(StartScreenOutput.ClickRegistration)
            }
            StartScreenIntent.CloseClick -> {
                onOutput(StartScreenOutput.CloseClick)
            }
        }
    }

    private fun onOutput(o: StartScreenOutput) {
        output(o)
    }
}
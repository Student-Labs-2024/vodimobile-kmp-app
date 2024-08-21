package com.vodimobile.presentation.store

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class GeneralViewModel : ViewModel() {
    val generalState = MutableStateFlow(GeneralState(selectedDate = longArrayOf(0L, 0L)))

    fun onIntent(intent: GeneralIntent) {
        when (intent) {
            is GeneralIntent.ChangeSelectedDate -> {
                generalState.update {
                    it.copy(selectedDate = intent.value)
                }
            }

            is GeneralIntent.ChangeAvailablePeriods -> {
                Log.d("TAG", intent.value.toString())
                generalState.update {
                    it.copy(availableDates = intent.value)
                }
            }
        }
    }
}
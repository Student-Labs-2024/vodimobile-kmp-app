package com.vodimobile.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.home.store.HomeScreenEffect
import com.vodimobile.presentation.screens.home.store.HomeScreenIntent
import com.vodimobile.presentation.screens.home.store.HomeScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeScreenViewModel : ViewModel() {

    val homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenEffect = MutableSharedFlow<HomeScreenEffect>()

    fun onIntent(intent: HomeScreenIntent) {
        when (intent) {
            else -> {}
        }
    }
}
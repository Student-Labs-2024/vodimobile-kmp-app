package com.vodimobile.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.home.store.HomeEffect
import com.vodimobile.presentation.screens.home.store.HomeIntent
import com.vodimobile.presentation.screens.home.store.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val homeState = MutableStateFlow(HomeState())
    val homeEffect = MutableSharedFlow<HomeEffect>()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.CarPreviewClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.CarPreviewClick)
                }
            }
            HomeIntent.FieldClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.FieldClick)
                }
            }
            HomeIntent.NotificationButtonClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.NotificationButtonClick)
                }
            }
        }
    }
}
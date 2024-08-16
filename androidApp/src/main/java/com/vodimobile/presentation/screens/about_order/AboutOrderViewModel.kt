package com.vodimobile.presentation.screens.about_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.about_order.store.AboutOrderEffect
import com.vodimobile.presentation.screens.about_order.store.AboutOrderIntent
import com.vodimobile.presentation.screens.about_order.store.AboutOrderState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AboutOrderViewModel : ViewModel() {
    val aboutOrderState = MutableStateFlow(AboutOrderState())
    val aboutOrderEffect = MutableSharedFlow<AboutOrderEffect>()

    fun onAboutOrderIntent(intent: AboutOrderIntent) {
        when (intent) {
            AboutOrderIntent.BackClick -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.BackClick)
                }
            }

            AboutOrderIntent.CanselOrder -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.ShowDialog)
                }
            }

            AboutOrderIntent.ChangeOrderClick -> {
                viewModelScope.launch {
                    aboutOrderEffect.emit(AboutOrderEffect.ChangeOrderClick)
                }
            }
        }
    }
}
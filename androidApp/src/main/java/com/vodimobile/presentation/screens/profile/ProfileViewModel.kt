package com.vodimobile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.profile.store.ProfileEffect
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    val profileEffect = MutableSharedFlow<ProfileEffect>()

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.AppExitClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.AppExitClick)
                }
            }

            ProfileIntent.ContactsClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.ContactsClick)
                }
            }

            ProfileIntent.FaqClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.FaqClick)
                }
            }

            ProfileIntent.PersonalDataClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.PersonalDataClick)
                }
            }

            ProfileIntent.RulesClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.RulesClick)
                }
            }
        }
    }
}
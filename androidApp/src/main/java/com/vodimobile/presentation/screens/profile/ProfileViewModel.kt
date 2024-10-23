package com.vodimobile.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.screens.profile.store.ProfileEffect
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import com.vodimobile.presentation.screens.profile.store.ProfileState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val dataStoreStorage: UserDataStoreStorage) : ViewModel() {
    val profileEffect = MutableSharedFlow<ProfileEffect>()
    val profileState = MutableStateFlow(ProfileState())
    private val supervisorIOCoroutineContext = Dispatchers.IO + SupervisorJob()

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
                    if (profileState.value.user == User.empty()) {
                        profileEffect.emit(ProfileEffect.Auth)
                    } else {
                        profileEffect.emit(ProfileEffect.PersonalDataClick)
                    }

                }
            }

            ProfileIntent.RulesClick -> {
                viewModelScope.launch {
                    profileEffect.emit(ProfileEffect.RulesClick)
                }
            }

            ProfileIntent.InitUser -> {
                viewModelScope.launch(context = supervisorIOCoroutineContext) {
                    dataStoreStorage.getUser().collect { value ->
                        withContext(viewModelScope.coroutineContext) {
                            profileState.update {
                                it.copy(user = value)
                            }
                        }
                    }
                }
            }

            ProfileIntent.ClearResources -> {
                supervisorIOCoroutineContext.cancelChildren()
            }
        }
    }
}

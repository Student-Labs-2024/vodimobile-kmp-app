package com.vodimobile.presentation.screens.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LogOutViewModel(private val userDataStoreStorage: UserDataStoreStorage) : ViewModel() {

    private val supervisorCoroutineContext = viewModelScope.coroutineContext + SupervisorJob()
    val logOutEffect = MutableSharedFlow<LogOutEffect>()

    fun onIntent(intent: LogOutIntent) {
        when(intent) {
            LogOutIntent.Cansel -> {
                viewModelScope.launch {
                    logOutEffect.emit(LogOutEffect.Cansel)
                }
            }
            LogOutIntent.LogOut -> {
                viewModelScope.launch(supervisorCoroutineContext) {
                    userDataStoreStorage.edit(user = User.empty())
                    logOutEffect.emit(LogOutEffect.LogOut)
                }
            }
        }
    }

}
package com.vodimobile.presentation.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.general.store.GeneralEffect
import com.vodimobile.presentation.general.store.GeneralIntent
import com.vodimobile.presentation.general.store.GeneralState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GeneralViewModel(
    private val userDataStoreStorage: UserDataStoreStorage
) : ViewModel() {
    val generalState = MutableStateFlow(GeneralState(selectedDate = longArrayOf(0L, 0L)))
    val generalEffect = MutableSharedFlow<GeneralEffect>()

    init {
        viewModelScope.launch {
            userDataStoreStorage.getUser().collect {
                if (it.phone.isEmpty()){
                    generalEffect.emit(GeneralEffect.UnauthedUser)
                }
            }
        }
    }

    fun onIntent(intent: GeneralIntent) {
        when (intent) {
            is GeneralIntent.ChangeSelectedDate -> {
                generalState.update {
                    it.copy(selectedDate = intent.value)
                }
            }

            is GeneralIntent.ChangeAvailablePeriods -> {
                generalState.update {
                    it.copy(availableDates = intent.value)
                }
            }

            is GeneralIntent.NoAuth -> {
                generalState.update {
                    it.copy(noAuth = false)
                }
            }
        }
    }
}

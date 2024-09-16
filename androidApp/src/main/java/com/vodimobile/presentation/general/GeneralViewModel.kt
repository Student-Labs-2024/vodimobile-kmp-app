package com.vodimobile.presentation.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.repository.hash.HashRepository
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.general.store.GeneralEffect
import com.vodimobile.presentation.general.store.GeneralIntent
import com.vodimobile.presentation.general.store.GeneralState
import com.vodimobile.utils.date_formats.parseToCrmDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GeneralViewModel(
    private val userDataStoreStorage: UserDataStoreStorage,
    private val crmStorage: CrmStorage,
    private val supabaseStorage: SupabaseStorage,
    private val hashRepository: HashRepository
) : ViewModel() {
    val generalState = MutableStateFlow(GeneralState(selectedDate = longArrayOf(0L, 0L)))
    val generalEffect = MutableSharedFlow<GeneralEffect>()

    private val supervisorIOCoroutineContext = Dispatchers.IO + SupervisorJob()

    init {
        viewModelScope.launch {
            userDataStoreStorage.getUser().collect {
                if (it.phone.isEmpty()) {
                    generalEffect.emit(GeneralEffect.UnauthedUser)
                } else {
                    doPrefetchAllCars(user = it)
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
                doPrefetchAllCarsByDate(selectedDate = generalState.value.selectedDate)
            }

            is GeneralIntent.ChangeAvailablePeriods -> {
                generalState.update {
                    it.copy(availableDates = intent.value)
                }
            }

            is GeneralIntent.NoAuth -> {7
                generalState.update {
                    it.copy(noAuth = false)
                }
            }
        }
    }

    private fun doPrefetchAllCars(user: User) {
        viewModelScope.launch(supervisorIOCoroutineContext) {
            val hashPassword = hashRepository.hash(text = user.password).decodeToString()
            val userFromRemote =
                supabaseStorage.getUser(password = hashPassword, phone = user.phone)
            crmStorage.getCarList(userFromRemote.accessToken, userFromRemote.refreshToken)
        }
    }

    private fun doPrefetchAllCarsByDate(selectedDate: LongArray) {
        viewModelScope.launch(supervisorIOCoroutineContext) {
            userDataStoreStorage.getUser().collect { user ->
                val hashPassword = hashRepository.hash(text = user.password).decodeToString()
                val userFromRemote =
                    supabaseStorage.getUser(password = hashPassword, phone = user.phone)
                crmStorage.getFreeCars(
                    accessToken = userFromRemote.accessToken,
                    refreshToken = userFromRemote.refreshToken,
                    carFreeListParamsDTO = CarFreeListParamsDTO(
                        begin = selectedDate[0].parseToCrmDateTime(),
                        end = selectedDate[1].parseToCrmDateTime(),
                        includeIdles = true,
                        includeReserves = true,
                        cityId = 2
                    )
                )
            }
        }
    }
}

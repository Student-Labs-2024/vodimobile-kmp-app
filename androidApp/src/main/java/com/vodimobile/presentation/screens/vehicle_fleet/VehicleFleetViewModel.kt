package com.vodimobile.presentation.screens.vehicle_fleet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


class VehicleFleetViewModel(
    private val crmStorage: CrmStorage,
    private val userDataStoreStorage: UserDataStoreStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {

    val vehicleState = MutableStateFlow(VehicleState())

    val vehicleFleetEffect = MutableSharedFlow<VehicleEffect>()

    fun onIntent(intent: VehicleIntent) {
        when (intent) {
            VehicleIntent.BackClick -> {
                viewModelScope.launch {
                    vehicleFleetEffect.emit(VehicleEffect.BackClick)
                }
            }

            is VehicleIntent.BookCarClick -> {
                viewModelScope.launch {
                    vehicleFleetEffect.emit(VehicleEffect.BookCarClick(carId = intent.car.carId))
                }
            }


            is VehicleIntent.InfoCarClick -> {
                viewModelScope.launch {
                    vehicleState.update {
                        it.copy(
                            showBottomSheet = true,
                            selectedCar = intent.car
                        )
                    }
                    vehicleFleetEffect.emit(VehicleEffect.InfoCarClick)
                }
            }

            VehicleIntent.OnSelected -> {

            }

            VehicleIntent.CloseModal -> {
                vehicleState.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            }

            VehicleIntent.InitCars -> {
                val userFLow = userDataStoreStorage.getUser()
                viewModelScope.launch {
                    userFLow.collect { user ->
                        val userFromRemote = supabaseStorage.getUser(password = user.password, phone = user.phone)
                        val crmEither = crmStorage.getCarList(
                            accessToken = userFromRemote.accessToken,
                            refreshToken = userFromRemote.refreshToken
                        )
                        vehicleState.update {
                            it.copy(crmEither = crmEither)
                        }
                    }
                }
            }

            VehicleIntent.DismissProgressDialog -> {
                viewModelScope.launch {
                    delay(1.seconds)
                    vehicleFleetEffect.emit(VehicleEffect.DismissLoadingDialog)
                }
            }

            VehicleIntent.ShowProgressDialog -> {
                viewModelScope.launch {
                    delay(1.seconds)
                    vehicleFleetEffect.emit(VehicleEffect.ShowLoadingDialog)
                }
            }
        }
    }
}
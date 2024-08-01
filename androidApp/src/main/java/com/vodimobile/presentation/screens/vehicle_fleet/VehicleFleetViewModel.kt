package com.vodimobile.presentation.screens.vehicle_fleet


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class VehicleFleetViewModel(
    carsStorage: CarsStorage,
    private val crmStorage: CrmStorage,
    private val userDataStoreStorage: UserDataStoreStorage
) : ViewModel() {

    val vehicleState = MutableStateFlow(
        VehicleState(
            carList = carsStorage.getPopularCars()
        )
    )

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
                    userFLow.collect{ user ->
                        vehicleFleetEffect.emit(VehicleEffect.ShowLoadingDialog)
                        val crmEither = crmStorage.getCarList(
                            accessToken = user.accessToken,
                            refreshToken = user.refreshToken
                        )
                        vehicleState.update {
                            it.copy(
                                carList = crmEither.data!!
                            )
                        }
                        vehicleFleetEffect.emit(VehicleEffect.DismissLoadingDialog)
                    }
                }
            }
        }
    }
}
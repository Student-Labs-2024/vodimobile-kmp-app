package com.vodimobile.presentation.screens.vehicle_fleet


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class VehicleFleetViewModel(carsStorage: CarsStorage) : ViewModel() {

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
        }
    }
}
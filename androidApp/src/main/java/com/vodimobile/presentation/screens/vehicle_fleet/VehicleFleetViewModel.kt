package com.vodimobile.presentation.screens.vehicle_fleet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListDTO
import com.vodimobile.domain.model.remote.dto.car_free_list.CarFreeListParamsDTO
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import com.vodimobile.utils.cars.carCategoryMap
import com.vodimobile.utils.date_formats.parseToCrmDate
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VehicleFleetViewModel(
    private val crmStorage: CrmStorage,
    private val userDataStoreStorage: UserDataStoreStorage,
    private val supabaseStorage: SupabaseStorage
) : ViewModel() {

    val vehicleState = MutableStateFlow(VehicleState())
    val vehicleFleetEffect = MutableSharedFlow<VehicleEffect>()
    private val supervisorCoroutineContext = viewModelScope.coroutineContext + SupervisorJob()

    fun onIntent(intent: VehicleIntent) {
        when (intent) {
            VehicleIntent.BackClick -> {
                viewModelScope.launch {
                    vehicleFleetEffect.emit(VehicleEffect.BackClick)
                }
            }

            is VehicleIntent.BookCarClick -> {
                viewModelScope.launch(context = supervisorCoroutineContext) {
                    userDataStoreStorage.getUser().collect { user ->
                        if (user == User.empty()) {
                            vehicleFleetEffect.emit(VehicleEffect.UnauthedUser)
                        } else {
                            vehicleFleetEffect.emit(VehicleEffect.BookCarClick(carId = intent.car.carId))
                        }
                    }
                }
            }

            is VehicleIntent.ShowModal -> {
                viewModelScope.launch {
                    vehicleState.update {
                        it.copy(
                            showBottomSheet = true,
                            selectedCar = intent.car
                        )
                    }
                }
            }

            is VehicleIntent.OnSelected -> {
                vehicleState.update { state ->
                    state.copy(
                        selectedIndex = intent.value,
                        filteredCars = vehicleState.value.cars.filter {
                            if (intent.value != 0)
                                it.carType.contains(carCategoryMap[intent.value])
                            else true
                        }
                    )
                }
            }

            VehicleIntent.CloseModal -> {
                vehicleState.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            }

            is VehicleIntent.InitCars -> {
                viewModelScope.launch(context = supervisorCoroutineContext) {
                    if (vehicleState.value.dateRange[0] == 0L && vehicleState.value.dateRange[1] == 0L) {
                        getAllCars()
                    } else {
                        getRangeCars(begin = intent.dateRange[0], end = intent.dateRange[1])
                    }
                }
            }

            VehicleIntent.DismissProgressDialog -> {
                viewModelScope.launch {
                    vehicleFleetEffect.emit(VehicleEffect.DismissLoadingDialog)
                }
            }

            VehicleIntent.ShowProgressDialog -> {
                viewModelScope.launch {
                    vehicleFleetEffect.emit(VehicleEffect.ShowLoadingDialog)
                }
            }

            is VehicleIntent.InitCarList -> {
                vehicleState.update {
                    it.copy(
                        cars = intent.value,
                        filteredCars = intent.value
                    )
                }
            }

            VehicleIntent.CancelCoroutines -> {
                supervisorCoroutineContext.cancelChildren()
            }

            is VehicleIntent.InitDateRange -> {
                vehicleState.update {
                    it.copy(
                        dateRange = intent.dateRange
                    )
                }
            }
        }
    }

    private suspend fun getAllCars() {
        val userFLow = userDataStoreStorage.getUser()
        userFLow.collect { user ->
            val userFromRemote =
                supabaseStorage.getUser(password = user.password, phone = user.phone)
            val crmEither = crmStorage.getCarList(
                accessToken = userFromRemote.accessToken,
                refreshToken = userFromRemote.refreshToken
            )

            when (crmEither) {
                is CrmEither.CrmData -> {
                    vehicleState.update {
                        it.copy(
                            isLoading = false,
                            cars = crmEither.data,
                            filteredCars = crmEither.data
                        )
                    }
                }

                is CrmEither.CrmError -> {
                    vehicleState.update {
                        it.copy(isLoading = false)
                    }
                    viewModelScope.launch {
                        vehicleFleetEffect.emit(VehicleEffect.ServerError)
                    }
                }

                CrmEither.CrmLoading -> {
                    vehicleState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private suspend inline fun getRangeCars(begin: Long, end: Long) {
        val userFLow = userDataStoreStorage.getUser()
        userFLow.collect { user ->
            val userFromRemote =
                supabaseStorage.getUser(password = user.password, phone = user.phone)

            val crmEitherFreeCars: CrmEither<CarFreeListDTO, HttpStatusCode> =
                crmStorage.getFreeCars(
                    accessToken = userFromRemote.accessToken,
                    refreshToken = userFromRemote.refreshToken,
                    carFreeListParamsDTO = CarFreeListParamsDTO(
                        begin = begin.parseToCrmDate(),
                        end = end.parseToCrmDate(),
                        includeIdles = true,
                        includeReserves = true,
                        cityId = 2
                    )
                )

            when (crmEitherFreeCars) {
                is CrmEither.CrmData -> {
                    val crmEither = crmStorage.getCarList(
                        accessToken = userFromRemote.accessToken,
                        refreshToken = userFromRemote.refreshToken,
                        carIds = crmEitherFreeCars.data.cars
                    )
                    when (crmEither) {
                        is CrmEither.CrmData -> {
                            vehicleState.update {
                                it.copy(
                                    isLoading = false,
                                    cars = crmEither.data,
                                    filteredCars = crmEither.data
                                )
                            }
                        }

                        is CrmEither.CrmError -> {
                            viewModelScope.launch {
                                vehicleFleetEffect.emit(VehicleEffect.ServerError)
                            }
                        }

                        CrmEither.CrmLoading -> {}
                    }
                }

                is CrmEither.CrmError -> {
                    vehicleState.update {
                        it.copy(isLoading = false)
                    }
                    viewModelScope.launch {
                        vehicleFleetEffect.emit(VehicleEffect.ServerError)
                    }
                }

                CrmEither.CrmLoading -> {
                    vehicleState.update {
                        it.copy(isLoading = true)
                    }
                }
            }

        }
    }
}

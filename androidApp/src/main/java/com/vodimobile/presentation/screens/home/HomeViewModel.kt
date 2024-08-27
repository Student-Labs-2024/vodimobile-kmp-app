package com.vodimobile.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.model.User
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.presentation.screens.home.store.HomeEffect
import com.vodimobile.presentation.screens.home.store.HomeIntent
import com.vodimobile.presentation.screens.home.store.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    carsStorage: CarsStorage,
    private val userDataStoreStorage: UserDataStoreStorage
) : ViewModel() {

    val homeState = MutableStateFlow(
        HomeState(
            carList = carsStorage.getPopularCars()
        )
    )
    val homeEffect = MutableSharedFlow<HomeEffect>()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ShowModal -> {
                homeState.update {
                    it.copy(
                        selectedCar = intent.car,
                        showBottomSheet = true
                    )
                }
            }

            HomeIntent.FieldClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.FieldClick)
                }
            }

            HomeIntent.NotificationButtonClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.NotificationButtonClick)
                }
            }

            HomeIntent.AllCarsClick -> {
                viewModelScope.launch {
                    homeEffect.emit(HomeEffect.AllCarsClick)
                }
            }

            HomeIntent.CloseModal -> {

                homeState.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }
            }

            is HomeIntent.BookCarClick -> {
                viewModelScope.launch {
                    userDataStoreStorage.getUser().collect { user->
                        if (user == User.empty()) {
                            homeEffect.emit(HomeEffect.OpenStartScreen)
                        } else {
                            homeEffect.emit(
                                HomeEffect.BookCarClick(
                                    carId = intent.carId
                                )
                            )
                        }
                    }
                }
            }

            HomeIntent.InitUser -> {
                viewModelScope.launch {
                    val userFlow = userDataStoreStorage.getUser()
                    userFlow.collect { value: User ->
                        if (value.phone.isEmpty()) {
                            homeEffect.emit(HomeEffect.UnauthedUser)
                        }
                    }
                }
            }
        }
    }
}

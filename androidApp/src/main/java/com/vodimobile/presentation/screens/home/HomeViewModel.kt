package com.vodimobile.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.home.store.HomeEffect
import com.vodimobile.presentation.screens.home.store.HomeIntent
import com.vodimobile.presentation.screens.home.store.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val homeState = MutableStateFlow(
        HomeState(
            carList = PopularCarsPreview.getPopularCarsPreview()
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
                    homeEffect.emit(HomeEffect.BookCarClick(carId = intent.car.carId))
                }
            }
        }
    }
}
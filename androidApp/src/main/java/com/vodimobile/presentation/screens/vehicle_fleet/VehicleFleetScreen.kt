package com.vodimobile.presentation.screens.vehicle_fleet

import BottomCard
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.repository.car.CarRepositoryImpl
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.storage.cars.CarsStorage
import com.vodimobile.domain.use_case.cars.GetPopularCarsUseCase
import com.vodimobile.presentation.components.AutoTypeTagList
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.components.cars_card.CardsSearch
import com.vodimobile.presentation.screens.home.store.HomeIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleFleetScreen(
    onVehicleIntent: (VehicleIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") vehicleEffect: MutableSharedFlow<VehicleEffect>,
    vehicleState: State<VehicleState>,
    navHostController: NavHostController,
    selectedTagIndex: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = Unit) {
        vehicleEffect.collect { effect ->
            when (effect) {
                VehicleEffect.BackClick -> {
                    navHostController.navigateUp()
                }

                is VehicleEffect.BookCarClick -> {

                }

                VehicleEffect.InfoCarClick -> {

                }

                VehicleEffect.CloseModel -> {

                }
            }
        }
    }
    ExtendedTheme {
        Scaffold(
            containerColor = ExtendedTheme.colorScheme.secondaryBackground,
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    ScreenHeader(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(top = 12.dp),
                        title = stringResource(R.string.vehicle_fleet),
                        onNavigateBack = {
                            onVehicleIntent(VehicleIntent.BackClick)
                        }
                    )

                    AutoTypeTagList(
                        modifier = Modifier,
                        tags = vehicleState.value.tags,
                        selectedTagIndex = selectedTagIndex
                    ) {

                    }
                }

            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(
                    20.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(vehicleState.value.carList) { _, item: Car ->
                    CardsSearch(
                        carItem = item,
                        onBookClick = { carItem -> onVehicleIntent(VehicleIntent.BookCarClick(car = carItem)) },
                        onInfoClick = { carItem -> onVehicleIntent(VehicleIntent.InfoCarClick(car = carItem)) })

                }
            }

            if (vehicleState.value.showBottomSheet) {
                BottomCard(
                    carItem = vehicleState.value.selectedCar,
                    onDismiss = { onVehicleIntent(VehicleIntent.CloseModal) },
                    onBookClick = { onVehicleIntent(VehicleIntent.BookCarClick(car = vehicleState.value.selectedCar)) }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun VehicleFleetScreenPreview() {
    VodimobileTheme(dynamicColor = false) {

        val vehicleFleetViewModel = VehicleFleetViewModel(
            CarsStorage(
                getPopularCarsUseCase = GetPopularCarsUseCase(
                    CarRepositoryImpl()
                )
            )
        )

        VehicleFleetScreen(
            onVehicleIntent = vehicleFleetViewModel::onIntent,
            vehicleEffect = vehicleFleetViewModel.vehicleFleetEffect,
            vehicleState = vehicleFleetViewModel.vehicleState.collectAsState(),
            selectedTagIndex = 0,
            navHostController = rememberNavController()
        )
    }
}
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.domain.use_case.crm.CreateBidUseCase
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarFreeDateRange
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.components.AutoTypeTagList
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.components.cars_card.CardsSearch
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleEffect
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleIntent
import com.vodimobile.presentation.screens.vehicle_fleet.store.VehicleState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleFleetScreen(
    onVehicleIntent: (VehicleIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") vehicleEffect: MutableSharedFlow<VehicleEffect>,
    vehicleState: State<VehicleState>,
    navHostController: NavHostController,
    selectedTagIndex: Int,
    dateRange: LongArray,
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

                VehicleEffect.DismissLoadingDialog -> {
                    navHostController.clearBackStack(route = DialogIdentifiers.LOADING_DIALOG)
                }

                VehicleEffect.ShowLoadingDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }
            }
        }
    }
    onVehicleIntent(VehicleIntent.InitCars(dateRange = dateRange))
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
                if (vehicleState.value.carList.isNotEmpty())
                    itemsIndexed(vehicleState.value.carList) { _, item: Car ->
                        CardsSearch(
                            carItem = item,
                            onBookClick = { carItem ->
                                onVehicleIntent(
                                    VehicleIntent.BookCarClick(
                                        car = carItem
                                    )
                                )
                            },
                            onInfoClick = { carItem ->
                                onVehicleIntent(
                                    VehicleIntent.InfoCarClick(
                                        car = carItem
                                    )
                                )
                            }
                        )
                    }
                else
                    onVehicleIntent(VehicleIntent.ShowProgressDialog)
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun VehicleFleetScreenPreview() {
    VodimobileTheme(dynamicColor = false) {

        val crmRepository = CrmRepositoryImpl()

        val crmStorage = CrmStorage(
            getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
            getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
            postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
            getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
            refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
            getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
            getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
            getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
            getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
            createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
        )

        val vehicleFleetViewModel = VehicleFleetViewModel(
            userDataStoreStorage = UserDataStoreStorage(
                editUserDataStoreUseCase = EditUserDataStoreUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(LocalContext.current)
                    )
                ),
                getUserDataUseCase = GetUserDataUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                preRegisterUserUseCase = PreRegisterUserUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                editPasswordUseCase = EditPasswordUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                )
            ),
            crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl()),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = CrmRepositoryImpl()),
                createBidUseCase = CreateBidUseCase(crmRepository = CrmRepositoryImpl())
            ),
            supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl(), crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl())
            )
        )

        VehicleFleetScreen(
            onVehicleIntent = vehicleFleetViewModel::onIntent,
            vehicleEffect = vehicleFleetViewModel.vehicleFleetEffect,
            vehicleState = vehicleFleetViewModel.vehicleState.collectAsState(),
            navHostController = rememberNavController(),
            selectedTagIndex = 0,
            dateRange = longArrayOf(0L, 0L)
        )
    }
}
package com.vodimobile.presentation.screens.reservation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.model.order.DateRange
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
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.HasUserWithPhoneUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCostUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCrmOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateNumberUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceFinishUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceStartUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateServicesUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafOrdersScreen
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.user_actions.PrimaryButton
import com.vodimobile.presentation.components.block.ScreenHeader
import com.vodimobile.presentation.screens.reservation.components.DateField
import com.vodimobile.presentation.screens.reservation.components.DropDownField
import com.vodimobile.presentation.screens.reservation.components.ReservationCarDescription
import com.vodimobile.presentation.screens.reservation.components.ServiceItemList
import com.vodimobile.presentation.screens.reservation.components.TimeField
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import com.vodimobile.presentation.screens.reservation.utils.DescribableItem
import com.vodimobile.presentation.screens.reservation.utils.TimeType
import com.vodimobile.presentation.general.store.GeneralIntent
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.DatePatterns.fullDateToStringRU
import com.vodimobile.shared.resources.SharedRes
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ReservationScreen(
    onReservationIntent: (ReservationIntent) -> Unit,
    onGeneralIntent: (GeneralIntent) -> Unit,
    reservationState: State<ReservationState>,
    @SuppressLint("ComposeMutableParameters") reservationEffect: MutableSharedFlow<ReservationEffect>,
    navHostController: NavHostController,
    startTime: String,
    endTime: String,
    date: LongArray,
    carId: Int
) {
    val defaultStatusForBid = stringResource(id = SharedRes.strings.cancelled_order.resourceId)
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }
    LaunchedEffect(key1 = Unit) {
        reservationEffect.collect { effect ->
            when (effect) {
                ReservationEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                ReservationEffect.DismissLoadingDialog -> {
                    navHostController.clearBackStack(route = DialogIdentifiers.LOADING_DIALOG)
                }

                ReservationEffect.ShowLoadingDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }

                ReservationEffect.PutBid -> {
                    navHostController.navigate(route = RootScreen.HOME_SCREEN)
                }

                is ReservationEffect.ShowTimePicker -> {
                    navHostController.navigate(route = "${DialogIdentifiers.TIME_SELECT_DIALOG}?timeType=${effect.type}")
                }

                ReservationEffect.ShowDatePicker -> {
                    navHostController.navigate(route= DialogIdentifiers.DATE_SELECT_DIALOG)
                }

                ReservationEffect.EmitGeneralStateChange -> {

                }

                ReservationEffect.Fail -> {
                    navHostController.navigate(route = LeafOrdersScreen.ERROR_APP_SCREEN)
                }
                ReservationEffect.Success -> {
                    navHostController.navigate(route = LeafOrdersScreen.SUCCESSFUL_SCREEN)
                }
            }
        }
    }

    SideEffect {
        onReservationIntent(ReservationIntent.GetCarFreeDate(value = date))

        onReservationIntent(ReservationIntent.DateChange(value = date))

        onReservationIntent(ReservationIntent.InitUser)

        onReservationIntent(ReservationIntent.GetAllPLaces)

        onReservationIntent(ReservationIntent.GetAllServices)

        onReservationIntent(ReservationIntent.CarIdChange(carId))
        onReservationIntent(ReservationIntent.GetAllCars)

        onReservationIntent(ReservationIntent.DateChange(date))

        onReservationIntent(ReservationIntent.StartTimeChange(startTime))
        onReservationIntent(ReservationIntent.EndTimeChange(endTime))

        onGeneralIntent(GeneralIntent.ChangeAvailablePeriods(value = reservationState.value.freeDates.map {
            DateRange(
                startDate = it.first,
                endDate = it.second
            )
        }))
    }

    SideEffect {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val isButtonClicked = remember { mutableStateOf(false) }
    fun resetButtonClicked() {
        if (isButtonClicked.value) isButtonClicked.value = false
    }

    val emptyInitialDateFlag = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(
                    id = R.string.title_reservation_screen
                ),
                onNavigateBack = {
                    onReservationIntent(ReservationIntent.ReturnBack)
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(padding)
        ) {
            ReservationCarDescription(
                car = reservationState.value.selectedCar,
                date = if (!emptyInitialDateFlag.value) fullDateToString(date) else
                    fullDateToString(
                        longArrayOf(0L, 0L)
                    ),
                modifier = Modifier.padding(top = 16.dp)
            )
            ExtendedTheme {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp
                    )
                ) {
                    if (date.contentEquals(longArrayOf(0L, 0L)) || emptyInitialDateFlag.value) {
                        emptyInitialDateFlag.value = true
                        item {
                            DateField(
                                label = stringResource(id = R.string.reservation_date_label),
                                value = fullDateToString(date),
                                placeholder = stringResource(id = R.string.reservation_date_placeholder),
                                onClick = {
                                    onReservationIntent(ReservationIntent.ShowDatePicker)
                                    resetButtonClicked()
                                },
                                isError = reservationState.value.errorDate && isButtonClicked.value,
                                messageError = stringResource(id = R.string.error_date)
                            )
                        }
                    }

                    item {
                        DropDownField(
                            label = stringResource(id = R.string.reservation_place_get_label),
                            value = reservationState.value.getPlace,
                            placeholder = stringResource(id = R.string.reservation_place_placeholder),
                            tooltip = stringResource(id = R.string.tooltip_day_night_time),
                            onValueChange = {
                                onReservationIntent(ReservationIntent.GetPlaceChange(it))
                                resetButtonClicked()
                            },
                            items = reservationState.value.placeList.map { item ->
                                DescribableItem(
                                    item.title,
                                    item.deliveryCost
                                )
                            }
                        )
                    }
                    item {
                        TimeField(
                            label = stringResource(id = R.string.reservation_time_from_label),
                            value = startTime,
                            placeholder = stringResource(id = R.string.reservation_time_placeholder),
                            onClick = {
                                resetButtonClicked()
                                onReservationIntent(ReservationIntent.ShowTimePicker(TimeType.START))
                            },
                            isError = reservationState.value.errorStartTime && isButtonClicked.value,
                            messageError = stringResource(id = R.string.error_time)
                        )
                    }
                    item {
                        DropDownField(
                            label = stringResource(id = R.string.reservation_place_return_label),
                            value = reservationState.value.returnPlace,
                            placeholder = stringResource(id = R.string.reservation_place_placeholder),
                            tooltip = stringResource(id = R.string.tooltip_day_night_time),
                            onValueChange = {
                                onReservationIntent(ReservationIntent.ReturnPlaceChange(it))
                                resetButtonClicked()
                            },
                            items = reservationState.value.placeList.map { item ->
                                DescribableItem(
                                    item.title,
                                    item.deliveryCost
                                )
                            }
                        )
                    }
                    item {
                        TimeField(
                            label = stringResource(id = R.string.reservation_time_to_label),
                            value = endTime,
                            placeholder = stringResource(id = R.string.reservation_time_placeholder),
                            onClick = {
                                resetButtonClicked()
                                onReservationIntent(ReservationIntent.ShowTimePicker(TimeType.END))
                            },
                            isError = reservationState.value.errorEndTime && isButtonClicked.value,
                            messageError = stringResource(id = R.string.empty_time)
                        )
                    }
                    item {
                        ServiceItemList(
                            label = stringResource(id = R.string.reservation_services_label),
                            serviceList = reservationState.value.serviceList,
                            selectedServiceIndexes = reservationState.value.selectedServiceIdList,
                            onSelected = {
                                onReservationIntent(
                                    ReservationIntent.ServiceIdChange(
                                        it
                                    )
                                )
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    item {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                                Text(
                                    text = stringResource(id = R.string.reservation_sum),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = reservationState.value.bidCost,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            PrimaryButton(
                                text = stringResource(id = R.string.reservation_put_bid),
                                enabled = reservationState.value.getPlace.isNotEmpty(),
                                onClick = {
                                    isButtonClicked.value = true
                                    if (reservationState.value.startTime.isNotEmpty() &&
                                        reservationState.value.endTime.isNotEmpty() &&
                                        !reservationState.value.date.contentEquals(
                                            longArrayOf(
                                                0L,
                                                0L
                                            )
                                        ) &&
                                        !reservationState.value.errorDate &&
                                        !reservationState.value.errorStartTime &&
                                        !reservationState.value.errorEndTime &&
                                        reservationState.value.bidCost.isNotEmpty()
                                    ) {
                                        onReservationIntent(
                                            ReservationIntent.PutBid(
                                                value = defaultStatusForBid,
                                                context = context
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun fullDateToString(date: LongArray): String {
    return if (date.contentEquals(longArrayOf(0L, 0L))) ""
    else fullDateToStringRU(date)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ReservationScreenLightPreview() {
    val crmStorage = CrmStorage(
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
    )
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val reservationViewModel = ReservationViewModel(
                dataStoreStorage = UserDataStoreStorage(
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
                crmStorage,
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(
                        SupabaseRepositoryImpl(),
                        crmStorage = crmStorage,
                        crmRepository = CrmRepositoryImpl()
                    ),
                    updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                    updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                    updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                    updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                    updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                    updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                    updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                    hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                onGeneralIntent = {},
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                date = longArrayOf(0L, 0L),
                startTime = "09:00",
                endTime = "16:30",
                carId = 17
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReservationScreenDarkPreview() {
    val crmStorage = CrmStorage(
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
    )
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val reservationViewModel = ReservationViewModel(
                dataStoreStorage = UserDataStoreStorage(
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
                crmStorage = crmStorage,
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(
                        SupabaseRepositoryImpl(),
                        crmStorage,
                        CrmRepositoryImpl()
                    ),
                    updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                    updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                    updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                    updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                    updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                    updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                    updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                    hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())

                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                onGeneralIntent = {},
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                date = longArrayOf(1724145226000, 1725009226000),
                startTime = "09:00",
                endTime = "16:30",
                carId = 17
            )
        }
    }
}

package com.vodimobile.presentation.screens.reservation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
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
import com.vodimobile.domain.use_case.supabase.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.reservation.components.DescriptionCommentField
import com.vodimobile.presentation.screens.reservation.components.DescriptionDateField
import com.vodimobile.presentation.screens.reservation.components.DescriptionPlaceField
import com.vodimobile.presentation.screens.reservation.components.DescriptionTimeField
import com.vodimobile.presentation.screens.reservation.components.ReservationCarDescription
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import com.vodimobile.presentation.screens.reservation.utils.TimeType
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.DatePatterns.fullDateToStringRU
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ReservationScreen(
    onReservationIntent: (ReservationIntent) -> Unit,
    reservationState: State<ReservationState>,
    @SuppressLint("ComposeMutableParameters") reservationEffect: MutableSharedFlow<ReservationEffect>,
    navHostController: NavHostController,
    startTime: String,
    endTime: String,
    date: LongArray,
    carId: Int
) {
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
                    navHostController.navigate(DialogIdentifiers.DATE_SELECT_DIALOG)
                }
            }
        }
    }

    onReservationIntent(ReservationIntent.GetAllPLaces)
    onReservationIntent(ReservationIntent.GetBidCost)

    if (reservationState.value.startTime.isNotEmpty() && reservationState.value.endTime.isNotEmpty()) {
        onReservationIntent(ReservationIntent.GetBidCost)
    }
    val isButtonClicked = remember { mutableStateOf(false) }

    fun resetButtonClicked() {
        if (isButtonClicked.value) isButtonClicked.value = false
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp),
                title = stringResource(
                    id = R.string.title_reservation_screen
                ),
                onNavigateBack = {
                    onReservationIntent(ReservationIntent.ReturnBack)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {
            ReservationCarDescription(
                car = reservationState.value.selectedCar,
                date = fullDateToString(date),
                modifier = Modifier.padding(top = 16.dp)
            )
            ExtendedTheme {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp
                    )
                ) {
                    if (date.contentEquals(longArrayOf(0L, 0L))) {
                        item {
                            DescriptionDateField(
                                label = stringResource(id = R.string.reservation_date_label),
                                value = fullDateToString(date),
                                placeholder = stringResource(id = R.string.reservation_date_placeholder),
                                onClick = { onReservationIntent(ReservationIntent.ShowDatePicker) },
                                isError = reservationState.value.errorDate && isButtonClicked.value,
                                messageError = stringResource(id = R.string.error_date)
                            )
                        }
                    }

                    item {
                        DescriptionPlaceField(
                            label = stringResource(id = R.string.reservation_place_label),
                            value = reservationState.value.place,
                            placeholder = stringResource(id = R.string.reservation_place_placeholder),
                            onValueChange = {
                                onReservationIntent(ReservationIntent.PlaceChange(it))
                                onReservationIntent(ReservationIntent.GetBidCost)
                                resetButtonClicked()
                            },
                            places = reservationState.value.placeList
                        )
                    }
                    item {
                        DescriptionTimeField(
                            label = stringResource(id = R.string.reservation_time_from_label),
                            value = startTime,
                            placeholder = stringResource(id = R.string.reservation_time_placeholder),
                            onClick = {
                                onReservationIntent(ReservationIntent.ShowTimePicker(TimeType.START))
                                resetButtonClicked()
                            },
                            isError = reservationState.value.errorStartTime && isButtonClicked.value,
                            messageError = stringResource(id = R.string.error_time)
                        )
                    }
                    item {
                        DescriptionTimeField(
                            label = stringResource(id = R.string.reservation_time_to_label),
                            value = endTime,
                            placeholder = stringResource(id = R.string.reservation_time_placeholder),
                            onClick = {
                                onReservationIntent(ReservationIntent.ShowTimePicker(TimeType.END))
                                resetButtonClicked()
                            },
                            isError = reservationState.value.errorEndTime && isButtonClicked.value,
                            messageError = stringResource(id = R.string.empty_time)
                        )
                    }
                    item {
                        DescriptionCommentField(
                            label = stringResource(id = R.string.reservation_comments_label),
                            value = reservationState.value.comments,
                            placeholder = stringResource(id = R.string.reservation_comments_placeholder),
                            onValueChange = {
                                onReservationIntent(ReservationIntent.CommentsChange(it))
                                resetButtonClicked()
                            }
                        )
                    }
                }
            }

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
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = reservationState.value.bidCost,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                PrimaryButton(
                    text = stringResource(id = R.string.reservation_put_bid),
                    enabled = reservationState.value.place.isNotEmpty(),
                    onClick = {
                        isButtonClicked.value = true
                        if (reservationState.value.startTime.isNotEmpty() &&
                            reservationState.value.endTime.isNotEmpty() &&
                            !reservationState.value.errorStartTime &&
                            !reservationState.value.errorEndTime
                        )
                            onReservationIntent(ReservationIntent.PutBid)
                    }
                )
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
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                    getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                    postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                    getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                    refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                    getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                    getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl())
                ),
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl())
                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                carId = 17,
                date = longArrayOf(0L, 0L),
                startTime = "09:00",
                endTime = "16:30"
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReservationScreenDarkPreview() {
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
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                    getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                    postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                    getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                    refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                    getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                    getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl())
                ),
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl())
                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                carId = 17,
                date = longArrayOf(0L, 0L),
                startTime = "09:00",
                endTime = "16:30"
            )
        }
    }
}

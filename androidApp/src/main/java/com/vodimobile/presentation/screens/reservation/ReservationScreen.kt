package com.vodimobile.presentation.screens.reservation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.remote.either.CrmEither
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditTokensUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.reservation.components.DescriptionCommentField
import com.vodimobile.presentation.screens.reservation.components.DescriptionPlaceField
import com.vodimobile.presentation.screens.reservation.components.DescriptionTimeField
import com.vodimobile.presentation.screens.reservation.components.ReservationCarDescription
import com.vodimobile.presentation.screens.reservation.store.ReservationEffect
import com.vodimobile.presentation.screens.reservation.store.ReservationIntent
import com.vodimobile.presentation.screens.reservation.store.ReservationState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ReservationScreen(
    onReservationIntent: (ReservationIntent) -> Unit,
    reservationState: State<ReservationState>,
    @SuppressLint("ComposeMutableParameters") reservationEffect: MutableSharedFlow<ReservationEffect>,
    navHostController: NavHostController,
    time: String,
    date: LongArray,
    car: Car
) {
    LaunchedEffect(key1 = Unit) {
        reservationEffect.collect { effect ->
            when (effect) {
                ReservationEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                ReservationEffect.DismissLoadingDialog -> {
                    navHostController.navigateUp()
                }

                ReservationEffect.ShowLoadingDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }

                ReservationEffect.PutBid -> {

                }

                ReservationEffect.ShowTimePicker -> {
                    navHostController.navigate(DialogIdentifiers.TIME_SELECT_DIALOG)
                }
            }
        }
    }

    onReservationIntent(ReservationIntent.GetAllPLaces)
    onReservationIntent(ReservationIntent.GetBidCost)

    when (val either = reservationState.value.places) {
        is CrmEither.CrmData -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 64.dp, horizontal = 16.dp)
            ) {
                ScreenHeader(
                    modifier = Modifier.padding(top = 16.dp),
                    title = stringResource(
                        id = R.string.title_reservation_screen
                    ),
                    onNavigateBack = {
                        onReservationIntent(ReservationIntent.ReturnBack)
                    }
                )
                ReservationCarDescription(
                    car = car,
                    date = date,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                ExtendedTheme {
                    DescriptionPlaceField(
                        label = stringResource(id = R.string.reservation_place_label),
                        value = reservationState.value.place,
                        placeholder = stringResource(id = R.string.reservation_place_placeholder),
                        onValueChange = {
                            onReservationIntent(ReservationIntent.PlaceChange(it))
                            onReservationIntent(ReservationIntent.GetBidCost)
                        },
                        items = either.data
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionTimeField(
                        label = stringResource(id = R.string.reservation_time_label),
                        value = time,
                        placeholder = stringResource(id = R.string.reservation_time_placeholder),
                        onValueChange = { onReservationIntent(ReservationIntent.GetBidCost) },
                        onClick = { onReservationIntent(ReservationIntent.ShowTimePicker) },
                        isError = reservationState.value.errorTime
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionCommentField(
                        label = stringResource(id = R.string.reservation_comments_label),
                        value = reservationState.value.time,
                        placeholder = stringResource(id = R.string.reservation_comments_placeholder),
                        onValueChange = { onReservationIntent(ReservationIntent.CommentsChange(it)) }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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
                            text = when (val eitherCost = reservationState.value.bidCost){
                                is CrmEither.CrmData -> eitherCost.data.cost.toString()
                                is CrmEither.CrmError -> {
                                    ""
                                }
                                CrmEither.CrmLoading -> {
                                    ""
                                }
                            },
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    PrimaryButton(
                        text = stringResource(id = R.string.reservation_put_bid),
                        enabled = reservationState.value.place.isNotEmpty(),
                        onClick = {
                            if (!reservationState.value.errorTime)
                                onReservationIntent(ReservationIntent.PutBid)
                        }
                    )
                }
            }
            onReservationIntent(ReservationIntent.DismissLoadingDialog)
        }

        is CrmEither.CrmError -> {
            onReservationIntent(ReservationIntent.DismissLoadingDialog)
        }

        CrmEither.CrmLoading -> {
            onReservationIntent(ReservationIntent.ShowLoadingDialog)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
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
                    ),
                    editTokensUseCase = EditTokensUseCase(
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
                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                car = Car.empty(),
                date = longArrayOf(0L, 0L),
                time = "09:00"
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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
                    ),
                    editTokensUseCase = EditTokensUseCase(
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
                )
            )
            ReservationScreen(
                onReservationIntent = reservationViewModel::onIntent,
                reservationState = reservationViewModel.reservationState.collectAsState(),
                reservationEffect = reservationViewModel.reservationEffect,
                navHostController = rememberNavController(),
                car = Car.empty(),
                date = longArrayOf(0L, 0L),
                time = "09:00"
            )
        }
    }
}

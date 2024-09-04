package com.vodimobile.presentation.screens.orders

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.vodimobile.domain.model.order.Order
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
import com.vodimobile.presentation.Anim
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafOrdersScreen
import com.vodimobile.presentation.components.SmallProgressDialogIndicator
import com.vodimobile.presentation.components.order_card.OrderCard
import com.vodimobile.presentation.screens.orders.components.NoOrders
import com.vodimobile.presentation.screens.orders.components.SegmentedButtonList
import com.vodimobile.presentation.screens.orders.store.OrderEffect
import com.vodimobile.presentation.screens.orders.store.OrderIntent
import com.vodimobile.presentation.screens.orders.store.OrderState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposeModifierMissing", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrdersScreen(
    orderIntent: (OrderIntent) -> Unit,
    orderState: State<OrderState>,
    @SuppressLint("ComposeMutableParameters") orderEffect: MutableSharedFlow<OrderEffect>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = Unit) {
        orderEffect.collect { effect ->
            when (effect) {
                is OrderEffect.InfoOrderClick -> {
                    navHostController.navigate(route = "${LeafOrdersScreen.ABOUT_ORDER_SCREEN}/${effect.orderId}")
                }

                OrderEffect.ShowProgressDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.SMALL_LOADING_DIALOG)
                }

                OrderEffect.DismissProgressDialog -> {
                    navHostController.navigateUp()
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        orderIntent(OrderIntent.InitCards)
    }
    DisposableEffect(key1 = Unit) {
       onDispose {
           orderIntent(OrderIntent.CanselAllCoroutines)
       }
    }
    ExtendedTheme {
        Scaffold(
            modifier = modifier
                .padding(bottom = 44.dp),
            containerColor = ExtendedTheme.colorScheme.secondaryBackground,
            topBar = {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(ExtendedTheme.colorScheme.secondaryBackground)
                ) {
                    CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(ExtendedTheme.colorScheme.secondaryBackground),
                        title = {
                            Text(
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ), text = stringResource(R.string.my_orders)
                            )
                        })
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        SegmentedButtonList(
                            tags = orderState.value.tags,
                            onSelected = {
                                orderIntent(OrderIntent.SelectOrders(index = it))
                            }
                        )
                    }
                }
            }) { paddingValues ->
            Spacer(modifier = Modifier.height(28.dp))
            if (orderState.value.orders.isEmpty()) {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = orderState.value.isLoading,
                        enter = fadeIn(animationSpec = tween(Anim.fastAnimDuration)),
                        exit = fadeOut(animationSpec = tween(Anim.fastAnimDuration))
                    ) {
                        SmallProgressDialogIndicator(modifier = Modifier.padding(top = 28.dp))
                    }
                    NoOrders(modifier = Modifier.padding(top = 100.dp))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(top = 28.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.Top
                    )
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimatedVisibility(
                                visible = orderState.value.isLoading,
                                enter = fadeIn(animationSpec = tween(Anim.fastAnimDuration)),
                                exit = fadeOut(animationSpec = tween(Anim.fastAnimDuration))
                            ) {
                                SmallProgressDialogIndicator(modifier = Modifier.padding(top = 28.dp))
                            }
                        }
                    }
                    if (!orderState.value.isLoading)
                        items(orderState.value.orders) { item ->
                            OrderCard(
                                orderItem = item,
                                onClick = {
                                    orderIntent(OrderIntent.InfoOrderClick(order = item))
                                }
                            )
                        }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun OrdersScreenLightPreview() {
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

        val supabaseStorage = SupabaseStorage(
            getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
            insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
            updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
            updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
            updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
            updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
            insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
            getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl(), crmStorage, crmRepository),
            updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
            updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
            updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
            updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
            updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
            updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
            updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
            hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
        )

        val userDataStoreStorage = UserDataStoreStorage(
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
        )

        val orderViewModel = OrderViewModel(
            supabaseStorage = supabaseStorage,
            userDataStoreStorage = userDataStoreStorage
        )
        OrdersScreen(
            orderIntent = orderViewModel::onIntent,
            orderState = orderViewModel.orderState.collectAsState(
                initial = OrderState(
                    orders = listOf(
                        Order.empty(), Order.empty()
                    )
                )
            ),
            orderEffect = orderViewModel.orderEffect,
            navHostController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OrdersScreenDarkPreview() {
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

        val supabaseStorage = SupabaseStorage(
            getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
            insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
            updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
            updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
            updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
            updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
            insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
            getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl(), crmStorage, crmRepository),
            updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
            updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
            updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
            updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
            updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
            updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
            updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
            hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
        )

        val userDataStoreStorage = UserDataStoreStorage(
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
        )

        val orderViewModel = OrderViewModel(
            supabaseStorage = supabaseStorage,
            userDataStoreStorage = userDataStoreStorage
        )
        OrdersScreen(
            orderIntent = orderViewModel::onIntent,
            orderState = orderViewModel.orderState.collectAsState(),
            orderEffect = orderViewModel.orderEffect,
            navHostController = rememberNavController()
        )
    }
}

package com.vodimobile.presentation.screens.about_order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.vodimobile.presentation.components.SmallProgressDialogIndicator
import com.vodimobile.presentation.screens.about_order.components.AboutCarItem
import com.vodimobile.presentation.screens.about_order.components.OrderStatusEnum
import com.vodimobile.presentation.screens.about_order.components.SumItem
import com.vodimobile.presentation.screens.about_order.store.AboutOrderEffect
import com.vodimobile.presentation.screens.about_order.store.AboutOrderIntent
import com.vodimobile.presentation.screens.about_order.store.AboutOrderState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutOrderScreen(
    aboutOrderState: State<AboutOrderState>,
    @SuppressLint("ComposeMutableParameters") aboutOrderEffect: MutableSharedFlow<AboutOrderEffect>,
    onAboutOrderIntent: (AboutOrderIntent) -> Unit,
    navHostController: NavHostController,
    orderId: Int,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = Unit) {
        aboutOrderEffect.collect { effect ->
            when (effect) {
                AboutOrderEffect.BackClick -> {
                    navHostController.navigateUp()
                }

                AboutOrderEffect.DismissDialog -> {
                    navHostController.navigateUp()
                }

                AboutOrderEffect.ShowDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.DELETE_ORDER_DIALOG)
                }

                AboutOrderEffect.ChangeOrderClick -> {

                }
            }
        }
    }

    SideEffect {
        onAboutOrderIntent(AboutOrderIntent.InitOrder(orderId = orderId))
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            onAboutOrderIntent(AboutOrderIntent.CanselCoroutines)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.about_the_order),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAboutOrderIntent(AboutOrderIntent.BackClick)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.header_back_content_desc),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                })
        }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = aboutOrderState.value.isLoading,
                        enter = fadeIn(animationSpec = tween(Anim.fastAnimDuration)),
                        exit = fadeOut(animationSpec = tween(Anim.fastAnimDuration))
                    ) {
                        SmallProgressDialogIndicator(modifier = Modifier.padding(top = 28.dp))
                    }
                }
            }
            item {
                AboutCarItem(car = aboutOrderState.value.order.car)
                OrderStatusEnum(order = aboutOrderState.value.order)
            }
            item {
                Divider(modifier = Modifier.fillMaxWidth())
                SumItem(cost = aboutOrderState.value.order.bid.cost)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AboutOrderScreenDarkPreview() {
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
            getOrdersUseCase = GetOrdersUseCase(
                SupabaseRepositoryImpl(),
                crmStorage,
                crmRepository
            ),
            updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
            updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
            updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
            updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
            updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
            updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
            updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
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

        val aboutOrderViewModel = AboutOrderViewModel(
            userDataStoreStorage = userDataStoreStorage, supabaseStorage = supabaseStorage
        )
        AboutOrderScreen(
            aboutOrderState = aboutOrderViewModel.aboutOrderState.collectAsState(),
            aboutOrderEffect = aboutOrderViewModel.aboutOrderEffect,
            onAboutOrderIntent = aboutOrderViewModel::onAboutOrderIntent,
            navHostController = rememberNavController(),
            orderId = 0
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun AboutOrderScreenLightPreview() {
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
            getOrdersUseCase = GetOrdersUseCase(
                SupabaseRepositoryImpl(),
                crmStorage,
                crmRepository
            ),
            updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
            updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
            updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
            updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
            updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
            updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
            updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
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

        val aboutOrderViewModel = AboutOrderViewModel(
            userDataStoreStorage = userDataStoreStorage, supabaseStorage = supabaseStorage
        )
        AboutOrderScreen(
            aboutOrderState = aboutOrderViewModel.aboutOrderState.collectAsState(),
            aboutOrderEffect = aboutOrderViewModel.aboutOrderEffect,
            onAboutOrderIntent = aboutOrderViewModel::onAboutOrderIntent,
            navHostController = rememberNavController(),
            orderId = 0
        )
    }
}

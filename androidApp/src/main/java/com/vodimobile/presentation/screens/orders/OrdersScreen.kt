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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.domain.model.order.Order
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
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposeModifierMissing", "UnusedMaterialScaffoldPaddingParameter")
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
                OrderEffect.InfoOrderClick -> {
                    navHostController.navigate(route = LeafOrdersScreen.ABOUT_ORDER_SCREEN)
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
    orderIntent(OrderIntent.InitCards)
    ExtendedTheme {
        Scaffold(
            modifier = modifier,
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
                            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
                    ) {
                        SegmentedButtonList(tags = orderState.value.tags,
                            onSelected = {})
                    }
                }
            }) { paddingValues ->
            Spacer(modifier = Modifier.height(28.dp))
            if (orderState.value.orders.isEmpty()) {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val visible by remember {
                        mutableStateOf(true)
                    }
                    AnimatedVisibility(
                        visible = visible,
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
        val orderViewModel = OrderViewModel()
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
        val orderViewModel = OrderViewModel()
        OrdersScreen(
            orderIntent = orderViewModel::onIntent,
            orderState = orderViewModel.orderState.collectAsState(),
            orderEffect = orderViewModel.orderEffect,
            navHostController = rememberNavController()
        )
    }
}

package com.vodimobile.presentation.screens.orders

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.screens.orders.components.SegmentedButtonList
import com.vodimobile.presentation.screens.orders.store.OrderState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposeModifierMissing", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrdersScreen(
    orderState: State<OrderState>,
    selectedTagIndex: Int,
    navHostController: NavHostController
) {
    ExtendedTheme {
        Scaffold(
            containerColor = ExtendedTheme.colorScheme.secondaryBackground,
            topBar = {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(ExtendedTheme.colorScheme.secondaryBackground)
                ) {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(ExtendedTheme.colorScheme.secondaryBackground),
                        title = {
                            Text(
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                ),
                                text = "Мои заказы"
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
                    ) {
                        SegmentedButtonList(
                            tags = orderState.value.tags, selectedTagIndex = selectedTagIndex
                        ) {

                        }
                    }
                }
            }
        ) { paddingValues ->
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    32.dp, alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    modifier = Modifier
                        .size(100.dp),
                    painter = painterResource(id = R.drawable.order),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        text = stringResource(R.string.order_title1)
                    )
                    Text(
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ExtendedTheme.colorScheme.greyLabel
                        ),
                        text = stringResource(R.string.order_titile2)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrdersScreenPreview() {
    VodimobileTheme(dynamicColor = false) {
        val orderViewModel = OrderViewModel()
        OrdersScreen(
            orderState = orderViewModel.orderState.collectAsState(),
            selectedTagIndex = 0,
            navHostController = rememberNavController())
    }
}

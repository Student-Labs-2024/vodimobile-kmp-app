package com.vodimobile.presentation.screens.about_order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.screens.about_order.components.AboutCarItem
import com.vodimobile.presentation.screens.about_order.components.ButtonsItem
import com.vodimobile.presentation.screens.about_order.components.OrderStatusEnum
import com.vodimobile.presentation.screens.about_order.components.SumItem
import com.vodimobile.presentation.screens.about_order.store.AboutOrderEffect
import com.vodimobile.presentation.screens.about_order.store.AboutOrderIntent
import com.vodimobile.presentation.screens.about_order.store.AboutOrderState
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutOrderScreen(
    aboutOrderState: State<AboutOrderState>,
    @SuppressLint("ComposeMutableParameters") aboutOrderEffect: MutableSharedFlow<AboutOrderEffect>,
    onAboutOrderIntent: (AboutOrderIntent) -> Unit,
    navHostController: NavHostController,
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
                AboutCarItem(car = aboutOrderState.value.order.car)
                OrderStatusEnum(order = aboutOrderState.value.order)
            }
            item {
                Divider(modifier = Modifier.fillMaxWidth())
                SumItem(cost = aboutOrderState.value.order.bid.cost)
                ButtonsItem(
                    onChangeClick = {},
                    onCanselClick = {
                        onAboutOrderIntent(AboutOrderIntent.CanselOrder)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AboutOrderScreenDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        val aboutOrderViewModel = AboutOrderViewModel()
        AboutOrderScreen(
            aboutOrderState = aboutOrderViewModel.aboutOrderState.collectAsState(),
            aboutOrderEffect = aboutOrderViewModel.aboutOrderEffect,
            onAboutOrderIntent = aboutOrderViewModel::onAboutOrderIntent,
            navHostController = rememberNavController()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun AboutOrderScreenLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        val aboutOrderViewModel = AboutOrderViewModel()
        AboutOrderScreen(
            aboutOrderState = aboutOrderViewModel.aboutOrderState.collectAsState(),
            aboutOrderEffect = aboutOrderViewModel.aboutOrderEffect,
            onAboutOrderIntent = aboutOrderViewModel::onAboutOrderIntent,
            navHostController = rememberNavController()
        )
    }
}
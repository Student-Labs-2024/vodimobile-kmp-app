package com.vodimobile.presentation.screens.about_order.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.domain.model.order.Order
import com.vodimobile.presentation.components.cars_card.CarSpecif
import com.vodimobile.presentation.components.order_card.OrderState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.DatePatterns

@Composable
fun OrderStatusEnum(order: Order, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(space = 22.dp, alignment = Alignment.Top)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.order_status),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1.0f)
            )
            OrderState(
                title = stringResource(id = order.status.title.resourceId),
                status = order.status
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.rent_period),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = DatePatterns.formatRentalPeriod(order = order),
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.where_take_auto),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = order.startLocation,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.what_time),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = DatePatterns.formatRentalTime(order = order),
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.labelMedium,
            text = stringResource(R.string.auto_charestiristics)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row {
                CarSpecif(
                    title = stringResource(R.string.transmis),
                    subtitle = stringResource(id = order.car.transmission.resourceId),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.spec1),
                            contentDescription = stringResource(R.string.transmis)
                        )
                    })
                CarSpecif(
                    title = stringResource(R.string.wheel_drive),
                    subtitle = stringResource(id = order.car.wheelDrive.resourceId),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.privod),
                            contentDescription = stringResource(R.string.wheel_drive)
                        )
                    })
            }
            Row {
                CarSpecif(title = stringResource(R.string.year_of_release),
                    subtitle = order.car.year.toString(),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.year),
                            contentDescription = stringResource(R.string.year_of_release)
                        )
                    })
                val tankValue = stringResource(id = order.car.tankValue.resourceId).toInt()
                CarSpecif(
                    title = stringResource(R.string.tank_value),
                    subtitle = (stringResource(
                        id = R.string.car,
                        tankValue
                    )),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.bak),
                            contentDescription = stringResource(R.string.tank_value)
                        )
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun OrderStatusEnumLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            OrderStatusEnum(order = Order.empty())
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OrderStatusEnumDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            OrderStatusEnum(order = Order.empty())
        }
    }
}

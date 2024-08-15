package com.vodimobile.presentation.components.order_card

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.order.Order
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.DatePatterns


@SuppressLint("ComposeModifierMissing")
@Composable
fun OrderCard(
    orderItem: Order,
    modifier: Modifier = Modifier,
    onClick: (Order) -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                OrderState(
                    title = stringResource(id = orderItem.status.title.resourceId),
                    status = orderItem.status
                )

                Image(
                    modifier = Modifier
                        .size(width = 147.dp, height = 48.dp),
                    painter = painterResource(id = orderItem.car.images[0].drawableResId),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp, alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = stringResource(id = orderItem.car.model.resourceId),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = DatePatterns.formatRentalPeriod(order = orderItem),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = stringResource(id = R.string.coast_order, orderItem.bid.cost),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    modifier = Modifier.padding(start = 12.dp),
                    imageVector = Icons.Rounded.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.about_the_order)
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderCardPreview() {
    VodimobileTheme(dynamicColor = false) {
        OrderCard(
            orderItem = Order.empty(),
            onClick = {}
        )
    }
}

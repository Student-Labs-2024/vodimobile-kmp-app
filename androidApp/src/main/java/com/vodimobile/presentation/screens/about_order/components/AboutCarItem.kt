package com.vodimobile.presentation.screens.about_order.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.domain.model.Car
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun AboutCarItem(car: Car, modifier: Modifier = Modifier) {
    ExtendedTheme {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colorScheme.containerBack
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 32.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 32.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Image(
                    painter = painterResource(
                        id = car.images[0].drawableResId
                    ),
                    contentDescription = stringResource(
                        id = car.model.resourceId
                    ),
                    modifier = Modifier
                        .size(width = 132.dp, height = 96.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 4.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.auto),
                        style = MaterialTheme.typography.labelMedium.copy(color = ExtendedTheme.colorScheme.hintText)
                    )

                    Text(
                        text = stringResource(id = car.model.resourceId),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun AboutCarItemLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        AboutCarItem(car = Car.empty())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AboutCarItemDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        AboutCarItem(car = Car.empty())
    }
}
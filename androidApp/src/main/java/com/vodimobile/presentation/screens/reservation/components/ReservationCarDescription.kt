package com.vodimobile.presentation.screens.reservation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun ReservationCarDescription(
    car: Car,
    date: String,
    modifier: Modifier = Modifier
) {

    ExtendedTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .then(modifier),
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colorScheme.containerBack
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 160.dp, height = 96.dp),
                    painter = painterResource(id = car.images[0].drawableResId),
                    contentDescription = stringResource(id = car.model.resourceId)
                )
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 12.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    CarDescriptionItem(
                        title = stringResource(id = R.string.desc_reservation_title),
                        subtitle = stringResource(id = car.model.resourceId)
                    )
                    if (date.isNotEmpty()) {
                        CarDescriptionItem(
                            title = stringResource(id = R.string.desc_reservation_subtitle),
                            subtitle = date
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ReservationCarDescLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        ReservationCarDescription(car = Car.empty(), date = "")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReservationCarDescDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        ReservationCarDescription(car = Car.empty(), date = "11-18 августа 2024")
    }
}

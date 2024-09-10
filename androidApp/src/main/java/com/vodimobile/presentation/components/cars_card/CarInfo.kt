import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.vodimobile.presentation.components.user_actions.PrimaryButton
import com.vodimobile.presentation.components.cars_card.CarSpecif
import com.vodimobile.presentation.components.cars_card.CarsTitle
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun CarInfo(
    onClick: () -> Unit,
    carItem: Car
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            modifier = Modifier
                .size(height = 120.dp, width = 340.dp),
            painter = painterResource(carItem.images[0].drawableResId),
            contentDescription = carItem.model.toString()
        )
        CarsTitle(
            title = stringResource(id = carItem.model.resourceId),
            subtitle = stringResource(
                R.string.tariff,
                carItem.tariffs.minBy { it.cost }.cost.toInt()
            )
        )

        Text(
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            text = stringResource(R.string.spec_tile)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row {
                CarSpecif(
                    title = stringResource(R.string.transmis),
                    subtitle = stringResource(id = carItem.transmission.resourceId),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.spec1),
                            contentDescription = stringResource(R.string.transmis)
                        )
                    })
                CarSpecif(
                    title = stringResource(R.string.wheel_drive),
                    subtitle = stringResource(id = carItem.wheelDrive.resourceId),
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
                    subtitle = carItem.year.toString(),
                    icon = {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.year),
                            contentDescription = stringResource(R.string.year_of_release)
                        )
                    })
                val tankValue = stringResource(id = carItem.tankValue.resourceId).toInt()
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

        PrimaryButton(
            text = stringResource(R.string.to_book),
            enabled = true,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CarInfoPreview() {
    VodimobileTheme(darkTheme = false, dynamicColor = false) {
        CarInfo({}, carItem = Car.empty())
    }
}

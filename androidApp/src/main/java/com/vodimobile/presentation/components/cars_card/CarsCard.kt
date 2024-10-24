import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@ExperimentalMaterial3Api
@Composable
fun CarsCard(
    onBookClick: (Car) -> Unit,
    onInfoClick: (Car) -> Unit,
    carItem: Car,
    modifier: Modifier = Modifier,
    isBookMode: Boolean = false
) {
    ExtendedTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .then(modifier),
            colors = CardDefaults.cardColors(ExtendedTheme.colorScheme.onSecondaryBackground),
            shape = RoundedCornerShape(22.dp),
            onClick = {
                onBookClick(carItem)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(height = 96.dp, width = 328.dp),
                    painter = painterResource(id = carItem.images[0].drawableResId),
                    contentDescription = null
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1.0f),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = carItem.model.resourceId),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.headlineSmall
                        )


                        Text(
                            modifier = Modifier,
                            text = stringResource(
                                id = R.string.tariff,
                                carItem.tariffs.minBy { it.cost }.cost.toInt()
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .size(40.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        onClick = {
                            onInfoClick(carItem)
                        }, shape = MaterialTheme.shapes.small
                    ) {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = ExtendedTheme.colorScheme.hintText
                        )
                    }
                }

                if (isBookMode)
                    PrimaryButton(
                        text = stringResource(R.string.to_book),
                        enabled = true,
                        onClick = { onBookClick(carItem) }
                    )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun CarsCardPreview() {
    VodimobileTheme(darkTheme = false, dynamicColor = false) {
        CarsCard(
            carItem = Car.empty(),
            onBookClick = {},
            onInfoClick = {}
        )
    }
}

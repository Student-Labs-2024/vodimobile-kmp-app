import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.domain.model.Car
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme


@Composable
fun BottomCard (
    carItem : Car
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp
            ),
        ) {
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                IconButton(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(containerColor = ExtendedTheme.colorScheme.hintText)
                ) {

                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                }
            }

            CarInfo({}, carItem)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomPreview() {
    VodimobileTheme(darkTheme = false, dynamicColor = false) {
        BottomCard(carItem = Car.empty())
    }
}
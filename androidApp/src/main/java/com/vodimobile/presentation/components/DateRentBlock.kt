package com.vodimobile.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun DateRentBlock(
    modifier: Modifier = Modifier,
    onFieldClick: () -> Unit,
    onButtonClick: () -> Unit,
    date: String,
    placeholder: String
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.choose_date_rent),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            DateRentField(
                date = date,
                placeholder = placeholder,
                onFieldClick = onFieldClick
            )
            PrimaryButton(
                text = stringResource(id = R.string.find_car_button),
                enabled = date.isNotEmpty(),
                onClick = { onButtonClick() }
            )
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DateRentBlockWithoutValueLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DateRentBlock(
            onFieldClick = {},
            onButtonClick = {},
            date = "",
            placeholder = "Когда?"
        )
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DateRentBlockWithValueLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DateRentBlock(
            onFieldClick = {},
            onButtonClick = {},
            date = "5-17 августа 2024",
            placeholder = "Когда?"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DateRentBlocWithoutValueNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DateRentBlock(
            onFieldClick = {},
            onButtonClick = {},
            date = "",
            placeholder = "Когда?"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DateRentBlocWithValueNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DateRentBlock(
            onFieldClick = {},
            onButtonClick = {},
            date = "5-17 августа 2024",
            placeholder = "Когда?"
        )
    }
}

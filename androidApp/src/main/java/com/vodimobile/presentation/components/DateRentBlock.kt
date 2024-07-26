package com.vodimobile.presentation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
    onNotificationClick: () -> Unit,
    date: String,
    placeholder: String
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NotificationIcon(onClick = onNotificationClick)

        Card(
            modifier = Modifier.wrapContentHeight(),
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
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DateRentBlockWithoutValueLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            DateRentBlock(
                onFieldClick = {},
                onButtonClick = {},
                onNotificationClick = {},
                date = "",
                placeholder = "Когда?"
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DateRentBlockWithValueLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            DateRentBlock(
                onFieldClick = {},
                onButtonClick = {},
                onNotificationClick = {},
                date = "5-17 августа 2024",
                placeholder = "Когда?"
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DateRentBlocWithoutValueNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            DateRentBlock(
                onFieldClick = {},
                onButtonClick = {},
                onNotificationClick = {},
                date = "",
                placeholder = "Когда?"
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DateRentBlocWithValueNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            DateRentBlock(
                onFieldClick = {},
                onButtonClick = {},
                onNotificationClick = {},
                date = "5-17 августа 2024",
                placeholder = "Когда?"
            )
        }
    }
}

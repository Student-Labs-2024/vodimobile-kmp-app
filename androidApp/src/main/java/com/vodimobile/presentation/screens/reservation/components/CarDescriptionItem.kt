package com.vodimobile.presentation.screens.reservation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun CarDescriptionItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .then(modifier),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun CarDescriptionItemLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        CarDescriptionItem(title = "Автомобиль", subtitle = "Hyundai Solaris")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarDescriptionItemDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        CarDescriptionItem(title = "Автомобиль", subtitle = "Hyundai Solaris")
    }
}

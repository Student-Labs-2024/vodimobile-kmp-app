package com.vodimobile.presentation.components.order_card

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.domain.model.order.CarStatus
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun OrderState(
    title : String?,
    status: CarStatus
) {
    ExtendedTheme {
        Card(
            modifier = Modifier
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraSmall,
            colors = CardDefaults.cardColors(
                when (status) {
                    CarStatus.Approved -> ExtendedTheme.colorScheme.approvedOrder
                    CarStatus.Processing -> ExtendedTheme.colorScheme.processingOrder
                    CarStatus.Cancelled -> ExtendedTheme.colorScheme.cancelledOrder
                    CarStatus.Completed -> ExtendedTheme.colorScheme.hintText
                }

            )
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 12.dp),
                color = Color.Black,
                text = title ?: "",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Preview
@Composable
private fun OrderStatePreview() {
    VodimobileTheme(dynamicColor = false) {
        OrderState(title = "", status = CarStatus.Processing)
    }
}

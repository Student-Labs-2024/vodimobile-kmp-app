package com.vodimobile.presentation.screens.delete_order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.theme.divider

@Composable
fun DeleteOrderDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.delete_order),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )
        },
        buttons = {
            Divider(
                color = divider,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(id = R.string.delete_order_back),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    )
                }
                TextButton(onClick = onConfirm) {
                    Text(
                        text = stringResource(id = R.string.delete_order_cansel),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    )
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .width(IntrinsicSize.Max),
        backgroundColor = MaterialTheme.colorScheme.onPrimary
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun DeleteOrderDialogLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            DeleteOrderDialog(onDismiss = {}, onConfirm = {})
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DeleteOrderDialogDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            DeleteOrderDialog(onDismiss = {}, onConfirm = {})
        }
    }
}
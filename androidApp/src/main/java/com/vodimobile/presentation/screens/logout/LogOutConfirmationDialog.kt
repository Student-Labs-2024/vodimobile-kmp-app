package com.vodimobile.presentation.screens.logout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun LogOutConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.log_out_confirmation_title),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.log_out_confirmation_body),
                color = MaterialTheme.colorScheme.outline,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.25.sp
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
                        text = stringResource(id = R.string.confirmation_cancel_button),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    )
                }
                TextButton(onClick = onConfirm) {
                    Text(
                        text = stringResource(id = R.string.confirmation_logout_button),
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

@Preview
@Composable
private fun LogOutConfirmationDialogPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            LogOutConfirmationDialog({}, {})
        }
    }
}

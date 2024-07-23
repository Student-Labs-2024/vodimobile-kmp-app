package com.vodimobile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
){
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun SecondaryButtonPreview() {
    VodimobileTheme(darkTheme = false,dynamicColor = false) {
        SecondaryButton(
            text = "Hello",
            enabled = false,
            onClick = {},
            )
    }
}
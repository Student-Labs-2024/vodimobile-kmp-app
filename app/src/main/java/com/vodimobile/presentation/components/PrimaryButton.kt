package com.vodimobile.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = text,
            modifier = modifier,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun PrimaryButton_test() {
    VodimobileTheme(darkTheme = false) {
        PrimaryButton(text = "Hello", enabled = false, onClick = {})
    }
}
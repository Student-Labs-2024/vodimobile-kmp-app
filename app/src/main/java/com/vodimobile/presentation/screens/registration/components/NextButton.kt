package com.vodimobile.presentation.screens.registration.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vodimobile.R

@Composable
fun NextButton(
    isEnabled: Boolean,
    onNextClicked: () -> Unit
) {
    Button(
        onClick = onNextClicked,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(15.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1958EE),
            disabledContainerColor = Color(0xFFA5BCF2),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(id = R.string.text_next_button),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

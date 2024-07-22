package com.vodimobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun AutoTypeTag(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(12.dp)
            ),
        onClick = onClick
    ) {
        Text(
            modifier = modifier,
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AutoTagButtonUnselectedPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = false, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AutoTagButtonSelectedPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = true, onClick = {})
    }
}

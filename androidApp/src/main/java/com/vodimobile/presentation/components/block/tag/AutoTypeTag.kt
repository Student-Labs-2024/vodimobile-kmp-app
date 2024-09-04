package com.vodimobile.presentation.components.block.tag

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun AutoTypeTag(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.wrapContentHeight(),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AutoTagButtonUnselectedLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = false, onClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AutoTagButtonSelectedLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = true, onClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AutoTagButtonUnselectedNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = false, onClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AutoTagButtonSelectedNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        AutoTypeTag(text = "Все авто", isSelected = true, onClick = {})
    }
}

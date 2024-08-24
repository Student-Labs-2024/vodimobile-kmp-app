package com.vodimobile.presentation.screens.about_order.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun ButtonsItem(
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
    onCanselClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        modifier = Modifier
            .padding(vertical = 24.dp)
            .then(modifier)
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.change_order),
            enabled = true,
            onClick = onChangeClick,
            modifier = Modifier.weight(1.0f)
        )
        ExtendedTheme {
            Button(
                onClick = onCanselClick,
                modifier = Modifier.wrapContentSize(),
                shape = MaterialTheme.shapes.large,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 13.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExtendedTheme.colorScheme.smsTextFieldBack,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    text = stringResource(id = R.string.delete_order_cansel),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ButtonsItemLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            ButtonsItem(onCanselClick = {}, onChangeClick = {})
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ButtonsItemDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            ButtonsItem(onCanselClick = {}, onChangeClick = {})
        }
    }
}

package com.vodimobile.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 11.dp)
            .then(modifier)
    ) {
        if (showBackButton)
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.header_back_content_desc),
                    modifier = Modifier
                        .size(32.dp)
                )
            }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1.0f)
        )

        if (showBackButton)
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Transparent,
                    containerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                enabled = false
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TopAppBarPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            TopAppBar(title = "Профиль", showBackButton = true, onBackClicked = {})
        }
    }
}
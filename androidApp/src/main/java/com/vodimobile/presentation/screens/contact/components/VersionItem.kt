package com.vodimobile.presentation.screens.contact.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun VersionItem(version: String, validYear: String) {

    ExtendedTheme {
        Card(
            modifier = Modifier,
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colorScheme.containerBack
            ),
            shape = RectangleShape

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .size(48.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 4.dp)
                            .size(48.dp),
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(R.string.version_str, version),
                        color = ExtendedTheme.colorScheme.hintText
                    )

                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = validYear + " " + stringResource(id = R.string.version1_str),
                        color = ExtendedTheme.colorScheme.hintText
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun VersionItemPreview() {
    VodimobileTheme {
        VersionItem("1.0", "2010")
    }
}

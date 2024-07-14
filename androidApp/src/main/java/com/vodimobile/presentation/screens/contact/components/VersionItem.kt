package com.vodimobile.presentation.screens.contact.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.BottomAppBarAlpha
import com.vodimobile.presentation.theme.VodimobileTheme


@Composable
fun VersionItem() {
    Card(
        modifier = Modifier,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer

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
                shape = MaterialTheme.shapes.small
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.logo3),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    style = MaterialTheme.typography.labelMedium,
                    text = stringResource(R.string.version_str),
                    color = MaterialTheme.colorScheme.onBackground.copy(BottomAppBarAlpha.BACKGROUND_ALPHA)
                )

                Text(
                    style = MaterialTheme.typography.labelMedium,
                    text = stringResource(R.string.version1_str),
                    color = MaterialTheme.colorScheme.onBackground.copy(BottomAppBarAlpha.BACKGROUND_ALPHA)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VersionItemPreview() {
    VodimobileTheme {
        VersionItem()
    }

}
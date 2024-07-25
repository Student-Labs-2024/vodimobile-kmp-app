package com.vodimobile.presentation.components.cars_card

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RowScope.CarSpecif(
    modifier: Modifier = Modifier,
    title: String?,
    subtitle: String?,
    icon: @Composable () -> Unit

) {
    ExtendedTheme {
        Row(
            modifier = Modifier
                .weight(1.0f)
                .wrapContentHeight()

                .padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon()

            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Text(
                    modifier = Modifier,
                    style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.onBackground),
                    text = title ?: ""
                )

                Text(
                    style = MaterialTheme.typography.displaySmall.copy(color = ExtendedTheme.colorScheme.hintText),
                    text = subtitle ?: ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CarSpecifPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Row {
            CarSpecif(title = "Коробка", subtitle = "АКПП-Автомат", icon = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.spec1),
                    contentDescription = null
                )
            })
            CarSpecif(title = "Коробка", subtitle = "АКПП-Автомат", icon = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.spec1),
                    contentDescription = null
                )
            })
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CarSpecifPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            Row {
                CarSpecif(title = "Коробка", subtitle = "АКПП-Автомат", icon = {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.spec1),
                        contentDescription = null
                    )
                })
                CarSpecif(title = "Коробка", subtitle = "АКПП-Автомат", icon = {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.spec1),
                        contentDescription = null
                    )
                })
            }
        }
    }
}
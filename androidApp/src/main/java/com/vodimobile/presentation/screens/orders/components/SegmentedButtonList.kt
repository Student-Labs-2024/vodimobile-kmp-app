package com.vodimobile.presentation.screens.orders.components


import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun SegmentedButtonList(
    @SuppressLint("ComposeUnstableCollections") tags: List<Int>,
    onSelected: (Int) -> Unit
) {
    val selectedIndex = remember {
        mutableStateOf(0)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(28.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            tags.forEachIndexed { index, item ->
                SegmentedButton(
                    text = stringResource(id = item),
                    isSelected = index == selectedIndex.value,
                    onClick = {
                        selectedIndex.value = index
                        onSelected(index)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SegmentedButtonListLightPreview() {
    VodimobileTheme(dynamicColor = false) {

        val tags = listOf(
            R.string.active_order,
            R.string.completed_order
        )
        SegmentedButtonList(
            tags = tags,
            onSelected = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SegmentedButtonListDarkPreview() {
    VodimobileTheme(dynamicColor = false) {

        val tags = listOf(
            R.string.active_order,
            R.string.completed_order
        )
        SegmentedButtonList(
            tags = tags,
            onSelected = { }
        )
    }
}

package com.vodimobile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun AutoTypeTagList(
    modifier: Modifier = Modifier,
    tags: List<String>,
    selectedTagIndex: Int,
    onSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(tags) { index, item ->
            AutoTypeTag(
                text = item,
                isSelected = index == selectedTagIndex,
                onClick = { onSelected(index) }
            )
        }
    }
}

@Preview
@Composable
fun AutoTypeTagListPreview() {
    VodimobileTheme(dynamicColor = false) {

        val tags = listOf(
            stringResource(id = R.string.auto_tag_all),
            stringResource(id = R.string.auto_tag_economy),
            stringResource(id = R.string.auto_tag_comfort),
            stringResource(id = R.string.auto_tag_premium),
            stringResource(id = R.string.auto_tag_sedan),
            stringResource(id = R.string.auto_tag_off_road)
        )

        var selectedTagIndex by remember { mutableIntStateOf(0) }

        AutoTypeTagList(
            tags = tags,
            selectedTagIndex = selectedTagIndex,
            onSelected = { index -> selectedTagIndex = index }
        )
    }
}

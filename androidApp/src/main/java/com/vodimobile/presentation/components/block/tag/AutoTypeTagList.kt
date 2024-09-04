package com.vodimobile.presentation.components.block.tag

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
    @SuppressLint("ComposeUnstableCollections") tags: List<Int>,
    selectedTagIndex: Int,
    modifier: Modifier = Modifier,
    onSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
            ),

        ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(tags) { index, item ->
                AutoTypeTag(
                    text = stringResource(id = item),
                    isSelected = index == selectedTagIndex,
                    onClick = { onSelected(index) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AutoTypeTagListPreview() {
    VodimobileTheme(dynamicColor = false) {

        val tags = listOf(
            R.string.auto_tag_all,
            R.string.auto_tag_economy,
            R.string.auto_tag_comfort,
            R.string.auto_tag_premium,
            R.string.auto_tag_sedan,
            R.string.auto_tag_off_road
        )

        var selectedTagIndex by remember { mutableIntStateOf(0) }

        AutoTypeTagList(
            tags = tags,
            selectedTagIndex = selectedTagIndex,
            onSelected = { index -> selectedTagIndex = index }
        )
    }
}

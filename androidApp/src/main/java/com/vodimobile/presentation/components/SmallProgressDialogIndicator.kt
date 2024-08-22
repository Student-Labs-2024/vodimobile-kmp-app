package com.vodimobile.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vodimobile.presentation.ProgressDialog
import com.vodimobile.presentation.SmallProgressDialog

@Composable
internal fun SmallProgressDialogIndicator(modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .size(SmallProgressDialog.PROGRESS_DIALOG_CARD_DP_SIZE),
    ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(all = SmallProgressDialog.PADDING),
            )
    }
}
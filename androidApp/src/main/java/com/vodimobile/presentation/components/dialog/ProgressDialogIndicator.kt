package com.vodimobile.presentation.components.dialog

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

@Composable
internal fun ProgressDialogIndicator() {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .size(ProgressDialog.PROGRESS_DIALOG_CARD_DP_SIZE),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(all = ProgressDialog.PADDING),
            )
        }
    }
}

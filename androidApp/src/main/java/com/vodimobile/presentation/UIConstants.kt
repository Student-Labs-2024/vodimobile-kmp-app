package com.vodimobile.presentation

import android.annotation.SuppressLint
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat

object BottomAppBarAlpha {
    const val BACKGROUND_ALPHA = 0.612f
}

object ProgressDialog {
    const val PROGRESS_DIALOG_ALPHA = 0.4f
    val PROGRESS_DIALOG_CARD_DP_SIZE = DpSize(width = 100.dp, height = 100.dp)
    val PADDING = 23.dp
}

object Anim{
    const val fastAnimDuration = 200
}

object DateFormat {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("dd MMMM yyyy")
}
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

object SmallProgressDialog {
    val PROGRESS_DIALOG_CARD_DP_SIZE = DpSize(width = 48.dp, height = 48.dp)
    val PADDING = 8.dp
}

object Anim{
    const val fastAnimDuration = 200
    const val SLOW_SCREEN_ANIM = 1000
    const val MEDIUM_SCREEN_ANIM = 700
}

object DateFormat {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("dd MMMM yyyy")
}

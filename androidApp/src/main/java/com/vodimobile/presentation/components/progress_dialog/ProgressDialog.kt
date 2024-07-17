package com.vodimobile.presentation.components.progress_dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.vodimobile.presentation.ProgressDialog
import com.vodimobile.presentation.screens.rules.RuleScreen
import com.vodimobile.presentation.screens.rules.RuleViewModel
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun ProgressDialog(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        content()
        if (isLoading)
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(LocalContentColor.current.copy(alpha = ProgressDialog.PROGRESS_DIALOG_ALPHA))
            ) {
                ProgressDialogIndicator()
            }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Загрузочно диалоговое окно, тёмная тема")
private fun ProgressDialogNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        ProgressDialog(isLoading = true) {
            RuleScreen(viewModel = RuleViewModel({}))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Загрузочно диалоговое окно, светлая тема")
private fun ProgressDialogLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        ProgressDialog(isLoading = true) {
            RuleScreen(viewModel = RuleViewModel({}))
        }
    }
}
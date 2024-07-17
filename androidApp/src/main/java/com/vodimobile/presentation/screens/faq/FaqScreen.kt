package com.vodimobile.presentation.screens.faq

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.android.R
import com.vodimobile.domain.model.FaqModel
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.faq.components.FaqHeader
import com.vodimobile.presentation.screens.faq.components.FaqItem
import com.vodimobile.presentation.screens.faq.store.FaqIntent
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun FaqScreen(faqViewModel: FaqViewModel) {

    val faqList: List<FaqModel> = FaqModel.getFaqList(LocalContext.current.resources)

    Scaffold(topBar = {
        ScreenHeader(
            title = stringResource(id = R.string.faq),
            onNavigateBack = { faqViewModel.onIntent(FaqIntent.BackClick) })
    }) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            item {
                FaqHeader()
            }

            itemsIndexed(faqList) { index, item ->
                FaqItem(faqModel = item)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FaqScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqScreen(faqViewModel = FaqViewModel({}))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaqScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqScreen(faqViewModel = FaqViewModel({}))
        }
    }
}
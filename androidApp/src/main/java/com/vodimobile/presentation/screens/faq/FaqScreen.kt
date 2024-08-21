package com.vodimobile.presentation.screens.faq

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.repository.faq.FaqRepositoryImpl
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.faq.components.FaqHeader
import com.vodimobile.presentation.screens.faq.components.FaqItem
import com.vodimobile.presentation.screens.faq.store.FaqEffect
import com.vodimobile.presentation.screens.faq.store.FaqIntent
import com.vodimobile.presentation.screens.faq.store.FaqState
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun FaqScreen(
    onFaqIntent: (FaqIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") faqEffect: MutableSharedFlow<FaqEffect>,
    faqState: State<FaqState>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = Unit) {
        faqEffect.collect { effect ->
            when (effect) {
                FaqEffect.BackClick -> {
                    navHostController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                title = stringResource(id = R.string.faq),
                onNavigateBack = { onFaqIntent(FaqIntent.BackClick) })
        },
        modifier = modifier
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            item {
                FaqHeader()
            }

            itemsIndexed(faqState.value.faqList) { index, item ->
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
            val faqViewModel: FaqViewModel = FaqViewModel(FaqRepositoryImpl(LocalContext.current))
            FaqScreen(
                faqViewModel::onIntent,
                faqViewModel.faqEffect,
                faqViewModel.faqState.collectAsState(),
                rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaqScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val faqViewModel: FaqViewModel = FaqViewModel(FaqRepositoryImpl(LocalContext.current))
            FaqScreen(
                faqViewModel::onIntent,
                faqViewModel.faqEffect,
                faqViewModel.faqState.collectAsState(),
                rememberNavController()
            )
        }
    }
}
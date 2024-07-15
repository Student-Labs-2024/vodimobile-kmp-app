package com.vodimobile.presentation.screens.faq.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.BottomAppBarAlpha
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun FaqHeader() {
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(stringResource(id = R.string.faq_title_1))
        }
        append(" ")
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(stringResource(id = R.string.faq_title_2))
        }
    }
    ExtendedTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(ExtendedTheme.colorScheme.headerBack)
                .padding(PaddingValues(horizontal = 28.dp, vertical = 32.dp)),
            verticalArrangement = Arrangement.spacedBy(
                space = 24.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            Text(
                text = annotatedString,
                modifier = textModifier,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.faq_subtitle),
                modifier = textModifier,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = BottomAppBarAlpha.BACKGROUND_ALPHA),
                textAlign = TextAlign.Start
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FaqHeaderLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqHeader()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaqHeaderNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqHeader()
        }
    }
}
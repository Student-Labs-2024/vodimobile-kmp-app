package com.vodimobile.presentation.screens.rule_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.splitTextIntoIntervalsByDelimiter

@Composable
fun RuleTitleItem(
    modifier: Modifier = Modifier,
    title: String
) {
    val (countWordsPart1, countWordsPart2) = Pair(2, 2)
    val delimiter = ' '
    val (titlePart1, titlePart2) = splitTextIntoIntervalsByDelimiter(
        text = title,
        delimiter = ' ',
        countWordsPart1 = countWordsPart1,
        countWordsPart2 = countWordsPart2
    )

    val resultTitle = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
            append(titlePart1)
        }
        append(delimiter)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(titlePart2)
        }
    }

    Text(
        text = resultTitle,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun RuleTitleItemPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.onPrimary) {
            val item = RulesAndConditionModel.getRulesAndConditionModelList(resources = LocalContext.current.resources)[0]
            RuleTitleItem(title = item.rule)
        }
    }
}

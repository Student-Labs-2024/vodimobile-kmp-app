package com.vodimobile.presentation.screens.rule_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.splitTextByDelimiter

@Composable
fun RuleTitleItem(
    modifier: Modifier = Modifier,
    title: String
) {
    val delimiter = '~'
    val (titlePart1, titlePart2) = splitTextByDelimiter(
        text = title,
        delimiter = delimiter,
    )

    val resultTitle = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(titlePart1)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(titlePart2)
        }
    }

    Text(
        text = resultTitle,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.3.sp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 12.dp)
            .testTag(tag = TestTags.RuleTitleItem.ruleTitleItemText),
        textAlign = TextAlign.Start
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun RuleTitleItemPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val rulesViewModel =
                RulesViewModel(rulesAndConditionRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            val item =
                rulesViewModel.rulesState.collectAsState().value.rulesList[0]
            RuleTitleItem(title = item.title)
        }
    }
}

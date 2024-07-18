package com.vodimobile.presentation.screens.rule_details

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.rule_details.components.RuleInformationItem
import com.vodimobile.presentation.screens.rule_details.components.RuleTitleItem
import com.vodimobile.presentation.screens.rule_details.store.RulesDetailsIntent
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RuleDetailsScreen(
    rules: List<RulesAndConditionModel>,
    ruleId: Int,
    viewModel: RulesDetailsViewModel
) {
    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(R.string.str_rules_and_conditions_title),
                onNavigateBack = {
                    viewModel.onIntent(RulesDetailsIntent.ReturnBack)
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 32.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RuleTitleItem(title = rules[ruleId].title)

            RuleInformationItem(
                condition = rules[ruleId].rule,
                conclusion = rules[ruleId].condition
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RuleDetailsScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            RuleDetailsScreen(
                ruleId = 0,
                rules = RulesAndConditionModel.getRulesAndConditionModelList(resources = LocalContext.current.resources),
                viewModel = RulesDetailsViewModel({})
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RuleDetailsScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            RuleDetailsScreen(
                ruleId = 0,
                rules = RulesAndConditionModel.getRulesAndConditionModelList(resources = LocalContext.current.resources),
                viewModel = RulesDetailsViewModel({})
            )
        }
    }
}

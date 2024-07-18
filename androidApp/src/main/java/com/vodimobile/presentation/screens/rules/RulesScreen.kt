package com.vodimobile.presentation.screens.rules

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.rules.components.RulesItem
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RuleScreen(viewModel: RuleViewModel, rules: List<RulesAndConditionModel>) {

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(R.string.str_rules_and_conditions_title), onNavigateBack = {
                    viewModel.onIntent(RulesIntent.BackClick)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            itemsIndexed(rules) { index, item ->
                RulesItem(title = item.title.replace("~", ""), onNavigate = {
                    viewModel.onIntent(RulesIntent.RuleClick(ruleId = index))
                })
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun RulesScreenPreview() {
    VodimobileTheme {
        RuleScreen(
            viewModel = RuleViewModel(output = {}),
            rules = RulesAndConditionModel.getRulesAndConditionModelList(resources = LocalContext.current.resources)
        )
    }
}
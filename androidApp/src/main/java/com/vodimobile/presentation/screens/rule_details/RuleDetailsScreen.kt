package com.vodimobile.presentation.screens.rule_details

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.presentation.components.block.ScreenHeader
import com.vodimobile.presentation.screens.rule_details.components.RuleInformationItem
import com.vodimobile.presentation.screens.rule_details.components.RuleTitleItem
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsEffect
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsIntent
import com.vodimobile.presentation.screens.rule_details.store.RuleDetailsState
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun RuleDetailsScreen(
    onRuleDetailsIntent: (RuleDetailsIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") ruleDetailsEffect: MutableSharedFlow<RuleDetailsEffect>,
    ruleDetailsState: State<RuleDetailsState>,
    navHostController: NavHostController,
    ruleId: Int,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = Unit) {
        ruleDetailsEffect.collect { effect ->
            when (effect) {
                RuleDetailsEffect.BackClick -> {
                    navHostController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(R.string.str_rules_and_conditions_title),
                onNavigateBack = {
                    onRuleDetailsIntent(RuleDetailsIntent.ReturnBack)
                }
            )
        }, modifier = modifier
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 32.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                RuleTitleItem(title = ruleDetailsState.value.rulesAndConditionModelList[ruleId].title)
            }
            item {
                RuleInformationItem(
                    condition = ruleDetailsState.value.rulesAndConditionModelList[ruleId].rule,
                    conclusion = ruleDetailsState.value.rulesAndConditionModelList[ruleId].condition
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RuleDetailsScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val ruleDetailsViewModel =
                RuleDetailsViewModel(ruleDetailsRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            RuleDetailsScreen(
                onRuleDetailsIntent = ruleDetailsViewModel::onIntent,
                ruleDetailsEffect = ruleDetailsViewModel.rulesDetailsEffect,
                ruleDetailsState = ruleDetailsViewModel.ruleDetailsState.collectAsState(),
                navHostController = rememberNavController(),
                ruleId = 0
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
            val ruleDetailsViewModel =
                RuleDetailsViewModel(ruleDetailsRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            RuleDetailsScreen(
                onRuleDetailsIntent = ruleDetailsViewModel::onIntent,
                ruleDetailsEffect = ruleDetailsViewModel.rulesDetailsEffect,
                ruleDetailsState = ruleDetailsViewModel.ruleDetailsState.collectAsState(),
                navHostController = rememberNavController(),
                ruleId = 0
            )
        }
    }
}

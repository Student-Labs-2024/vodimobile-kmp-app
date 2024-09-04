package com.vodimobile.presentation.screens.rules

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.components.block.ScreenHeader
import com.vodimobile.presentation.screens.rules.components.RulesItem
import com.vodimobile.presentation.screens.rules.store.RulesEffect
import com.vodimobile.presentation.screens.rules.store.RulesState
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ComposeModifierMissing")
@Composable
fun RuleScreen(
    onRulesIntent: (RulesIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") rulesEffect: MutableSharedFlow<RulesEffect>,
    rulesState: State<RulesState>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = Unit) {
        rulesEffect.collect { effect ->
            when (effect) {
                RulesEffect.BackClick -> {
                    navHostController.navigateUp()
                }

                is RulesEffect.RuleClick -> {
                    navHostController.navigate("${LeafScreen.RULE_DETAILS_SCREEN}/${effect.ruleId}")
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
                    onRulesIntent(RulesIntent.BackClick)
                }
            )
        }, modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            itemsIndexed(rulesState.value.rulesList) { index, item ->
                RulesItem(title = item.title.replace("~", ""),
                    onNavigate = {
                        onRulesIntent(RulesIntent.RuleClick(ruleId = index))
                    }
                )
            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RulesScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val rulesViewModel =
                RulesViewModel(rulesAndConditionRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            RuleScreen(
                onRulesIntent = rulesViewModel::onIntent,
                rulesEffect = rulesViewModel.rulesEffect,
                rulesState = rulesViewModel.rulesState.collectAsState(),
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RulesScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val rulesViewModel =
                RulesViewModel(rulesAndConditionRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            RuleScreen(
                onRulesIntent = rulesViewModel::onIntent,
                rulesEffect = rulesViewModel.rulesEffect,
                rulesState = rulesViewModel.rulesState.collectAsState(),
                navHostController = rememberNavController()
            )
        }
    }
}

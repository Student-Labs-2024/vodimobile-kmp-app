package com.vodimobile.presentation.screens.rules

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.model.mockList
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.rules.components.RulesItem
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RulesScreen(viewModel: RulesViewModel) {
    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 23.dp),
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
            itemsIndexed(mockList) { index, item ->
                RulesItem(title = stringResource(id = item.title), onNavigate = {
                    viewModel.onIntent(RulesIntent.RuleClick(rulesAndCondition = item))
                })
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun RulesScreenPreview() {
    VodimobileTheme {
        RulesScreen(viewModel = RulesViewModel())
    }
}
package com.vodimobile.presentation.screens.rule_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.screens.rules.RuleScreen
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RuleInformationItem(
    modifier: Modifier = Modifier,
    condition: String,
    conclusion: String
) {
    ExtendedTheme {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colorScheme.containerBack
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = condition,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.fillMaxWidth()
                )
                Text(
                    text = conclusion,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Preview
@Composable
private fun RulesInformationItemPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.onPrimary) {
            val rulesViewModel =
                RulesViewModel(rulesAndConditionRepository = RulesAndConditionRepositoryImpl(context = LocalContext.current))
            val item = rulesViewModel.rulesState.value.rulesList[0]
            RuleInformationItem(
                condition = item.title,
                conclusion = item.condition
            )
        }
    }
}

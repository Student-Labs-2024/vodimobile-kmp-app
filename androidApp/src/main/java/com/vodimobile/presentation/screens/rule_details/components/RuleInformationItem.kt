package com.vodimobile.presentation.screens.rule_details.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.theme.containerBackLight

@Composable
fun RuleInformationItem(
    modifier: Modifier = Modifier,
    condition: String,
    conclusion: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerBackLight
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

@Preview
@Composable
private fun RulesInformationItemPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.onPrimary) {
            val item = RulesAndConditionModel.getRulesAndConditionModelList(resources = LocalContext.current.resources)[0]
            RuleInformationItem(
                condition = item.title,
                conclusion = item.condition
            )
        }
    }
}

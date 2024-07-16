package com.vodimobile.presentation.screens.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun ContactsFaqRulesBlock(
    modifier: Modifier = Modifier,
    onRulesClick: () -> Unit,
    onFAQClick: () -> Unit,
    onContactsClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = ExtendedTheme.colorScheme.onSecondaryBackground
        )
    ) {
        ContactsFaqRulesItem(
            onClick = onRulesClick,
            title = stringResource(id = R.string.rules_and_conditions)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rules_grey),
                contentDescription = stringResource(id = R.string.rules_and_conditions),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.612f)
            )
        }
        ContactsFaqRulesItem(
            title = stringResource(id = R.string.faq),
            onClick = onFAQClick
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(id = R.string.faq),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.612f)
            )
        }
        ContactsFaqRulesItem(
            title = stringResource(id = R.string.contacts),
            onClick = onContactsClick
        ) {
            Icon(
                imageVector = Icons.Outlined.MailOutline,
                contentDescription = stringResource(id = R.string.contacts),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.612f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AnotherDataBlockPreview() {
    VodimobileTheme(dynamicColor = false) {
        ContactsFaqRulesBlock(modifier = Modifier, {}, {}, {})
    }
}
package com.vodimobile.presentation.screens.rule_details.components

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.theme.VodimobileTheme
import org.junit.Rule
import org.junit.Test


class RuleTitleItemKtTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun ruleTitleItemCenterTitleTextTest_EN() {
        rule.setContent {
            VodimobileTheme(dynamicColor = false) {
                RuleTitleItem(title = "Payment and deposit ~of the reserved car")
            }
        }

        rule.onNode(hasTestTag(TestTags.RuleTitleItem.ruleTitleItemText)).assertTextEquals("Payment and deposit of the reserved car")
    }

    @Test
    fun ruleTitleItemEndTitleTextTest_EN() {
        rule.setContent {
            VodimobileTheme(dynamicColor = false) {
                RuleTitleItem(title = "The territory of movement~")
            }
        }

        rule.onNode(hasTestTag(TestTags.RuleTitleItem.ruleTitleItemText)).assertTextEquals("The territory of movement")
    }
}
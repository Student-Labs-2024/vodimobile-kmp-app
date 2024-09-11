package com.vodimobile.presentation.screens.rule_details.components

import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import com.vodimobile.android.R
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.theme.VodimobileTheme
import org.junit.Rule
import org.junit.Test


class RuleTitleItemKtTest {

    @get:Rule
    val rule = createComposeRule()

    private val resId = R.array.rules
    private var item: String = ""

    @Test
    fun ruleTitleItemCenterTitleTextTest() {
        rule.setContent {
            VodimobileTheme(dynamicColor = false) {
                item = stringArrayResource(id = resId)[3]
                RuleTitleItem(title = item)
            }
        }

        rule.onNode(hasTestTag(TestTags.RuleTitleItem.ruleTitleItemText)).assertTextEquals(item.replace("~", ""))
    }

    @Test
    fun ruleTitleItemEndTitleTextTest() {
        rule.setContent {
            VodimobileTheme(dynamicColor = false) {
                item = stringArrayResource(id = resId)[8]
                RuleTitleItem(title = item)
            }
        }

        rule.onNode(hasTestTag(TestTags.RuleTitleItem.ruleTitleItemText)).assertTextEquals(item.replace("~", ""))
    }
}

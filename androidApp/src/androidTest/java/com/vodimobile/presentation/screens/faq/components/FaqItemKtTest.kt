package com.vodimobile.presentation.screens.faq.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.vodimobile.domain.model.FaqModel
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.theme.VodimobileTheme
import org.junit.Rule
import org.junit.Test

class FaqItemKtTest {

    @get:Rule
    val rule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun faqItem() {
        rule.setContent {
            VodimobileTheme(dynamicColor = false) {
                FaqItem(
                    faqModel = FaqModel(
                        question = "Куда можно ездить?",
                        answer = "Привет"
                    )
                )
            }
        }

        rule.onNode(hasTestTag(TestTags.FaqItem.questionCard)).performClick()
        rule.waitUntilExactlyOneExists(hasTestTag(TestTags.FaqItem.answerText))
    }
}
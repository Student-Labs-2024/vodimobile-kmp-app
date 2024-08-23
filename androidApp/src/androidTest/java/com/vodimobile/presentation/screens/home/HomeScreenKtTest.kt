package com.vodimobile.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performScrollToIndex
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.screens.home.components.SnapVodimobileTopAppBar
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun homeScreenFlexibleAppBarTestBehavior() {
        rule.setContent {
            Scaffold(
                topBar = {
                    SnapVodimobileTopAppBar(
                        date = longArrayOf(System.currentTimeMillis(),System.currentTimeMillis()),
                        onNotificationButtonClick = { /*TODO*/ },
                        onFieldClick = { /*TODO*/ },
                        onButtonClick = {},
                        modifier = Modifier.testTag(TestTags.HomeScreen.flexibleTopAppBar)
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .testTag(TestTags.HomeScreen.contentScroll)
                ) {
                    repeat(100) {
                        item {
                            Text(text = "$it")
                        }
                    }
                }
            }
        }

        rule.onNode(
            hasTestTag(TestTags.HomeScreen.contentScroll)
                    and
                    hasScrollAction()
        ).performScrollToIndex(50)

        rule.onNode(hasTestTag(TestTags.HomeScreen.flexibleTopAppBar)).assertDoesNotExist()
    }
}

package com.vodimobile.presentation.screens.faq.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.domain.model.FaqModel
import com.vodimobile.presentation.screens.faq.Dimensions
import com.vodimobile.presentation.theme.VodimobileTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.theme.divider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqItem(faqModel: FaqModel) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val degrees by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "",
        animationSpec = tween(Dimensions.animationDuration)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 16.dp, vertical = 6.dp))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag = TestTags.FaqItem.questionCard),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = if (expanded) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
            ),
            onClick = { expanded = !expanded },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(horizontal = 12.dp, vertical = 9.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 12.dp,
                        alignment = Alignment.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = faqModel.question,
                        modifier = Modifier
                            .weight(1.0f),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        modifier = Modifier.rotate(degrees),
                        contentDescription = "",
                        tint = LocalContentColor.current.copy(alpha = Dimensions.faqIconButtonAlpha)
                    )
                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(animationSpec = tween(Dimensions.animationDuration)),
                    exit = fadeOut(animationSpec = tween(Dimensions.animationDuration))
                ) {
                    Text(
                        text = faqModel.answer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .testTag(tag = TestTags.FaqItem.answerText),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = divider
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FaqItemLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqItem(faqModel = FaqModel.getFaqList(LocalContext.current.resources)[0])
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaqItemNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            FaqItem(faqModel = FaqModel.getFaqList(LocalContext.current.resources)[0])
        }
    }
}
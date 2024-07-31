package com.vodimobile.presentation.screens.profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.screens.profile.components.ContactsFaqRulesBlock
import com.vodimobile.presentation.screens.profile.components.ExitBlock
import com.vodimobile.presentation.screens.profile.components.PersonalDataCard
import com.vodimobile.presentation.screens.profile.store.ProfileEffect
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ProfileScreen(
    onProfileIntent: (ProfileIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") profileEffect: MutableSharedFlow<ProfileEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        profileEffect.collect { effect ->
            when (effect) {
                ProfileEffect.AppExitClick -> {
                    navHostController.navigate(route = DialogIdentifiers.LOG_OUT_DIALOG)
                }

                ProfileEffect.ConstantsClick -> {
                    navHostController.navigate(route = LeafScreen.CONTACTS_SCREEN)
                }

                ProfileEffect.FaqClick -> {
                    navHostController.navigate(route = LeafScreen.FAQ_SCREEN)
                }

                ProfileEffect.PersonalDataClick -> {
                    navHostController.navigate(route = LeafScreen.EDIT_PROFILE)
                }

                ProfileEffect.RulesClick -> {
                    navHostController.navigate(route = LeafScreen.RULES_SCREEN)
                }
            }
        }
    }

    ExtendedTheme {
        Scaffold(
            containerColor = ExtendedTheme.colorScheme.secondaryBackground,
            topBar = {
                Text(
                    text = stringResource(id = R.string.profile_title),
                    style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .padding(vertical = 11.dp)
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PersonalDataCard(
                    onEditClick = {
                        onProfileIntent(ProfileIntent.PersonalDataClick)
                    }
                )
                ContactsFaqRulesBlock(
                    onRulesClick = {
                        onProfileIntent(ProfileIntent.RulesClick)
                    },
                    onFAQClick = {
                        onProfileIntent(ProfileIntent.FaqClick)
                    },
                    onContactsClick = {
                        onProfileIntent(ProfileIntent.ConstantsClick)
                    }
                )
                ExitBlock(
                    onClick = {
                        onProfileIntent(ProfileIntent.AppExitClick)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ProfileScreenPreviewLight() {
    VodimobileTheme {
        val profileViewModel = ProfileViewModel()
        ProfileScreen(
            onProfileIntent = profileViewModel::onIntent,
            profileEffect = profileViewModel.profileEffect,
            navHostController = rememberNavController()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProfileScreenPreviewNight() {
    VodimobileTheme {
        val profileViewModel = ProfileViewModel()
        ProfileScreen(
            onProfileIntent = profileViewModel::onIntent,
            profileEffect = profileViewModel.profileEffect,
            navHostController = rememberNavController()
        )
    }
}
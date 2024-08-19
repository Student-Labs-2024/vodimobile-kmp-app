package com.vodimobile.presentation.screens.profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.screens.profile.components.ExitBlock
import com.vodimobile.presentation.screens.profile.components.ProfileItemBlock
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

                ProfileEffect.ContactsClick -> {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = 40.dp)
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
                    ) {
                        ProfileItemBlock(
                            title = stringResource(id = R.string.personal_data_title),
                            onClick = { onProfileIntent(ProfileIntent.PersonalDataClick) },
                            icon = {
                                Image(
                                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                                    painter = painterResource(id = R.drawable.user),
                                    contentDescription = stringResource(id = R.string.personal_data_title)
                                )
                            }
                        )
                        ProfileItemBlock(
                            title = stringResource(id = R.string.rules_and_conditions),
                            onClick = { onProfileIntent(ProfileIntent.RulesClick) },
                            icon = {
                                Image(
                                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                                    painter = painterResource(id = R.drawable.rules),
                                    contentDescription = stringResource(id = R.string.rules_and_conditions)
                                )
                            }
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
                    ) {
                        ProfileItemBlock(
                            title = stringResource(id = R.string.faq),
                            onClick = { onProfileIntent(ProfileIntent.FaqClick) },
                            icon = {
                                Image(
                                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                                    painter = painterResource(id = R.drawable.help),
                                    contentDescription = stringResource(id = R.string.faq)
                                )
                            }
                        )
                        ProfileItemBlock(
                            title = stringResource(id = R.string.contacts),
                            onClick = { onProfileIntent(ProfileIntent.ContactsClick) },
                            icon = {
                                Image(
                                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                                    painter = painterResource(id = R.drawable.contacts),
                                    contentDescription = stringResource(id = R.string.contacts)
                                )
                            }
                        )
                    }
                }
                item {
                    ExitBlock(
                        onClick = {
                            onProfileIntent(ProfileIntent.AppExitClick)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ProfileScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
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
    VodimobileTheme(dynamicColor = false) {
        val profileViewModel = ProfileViewModel()
        ProfileScreen(
            onProfileIntent = profileViewModel::onIntent,
            profileEffect = profileViewModel.profileEffect,
            navHostController = rememberNavController()
        )
    }
}

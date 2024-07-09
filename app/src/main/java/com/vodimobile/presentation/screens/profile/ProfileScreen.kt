package com.vodimobile.presentation.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.components.TopAppBar
import com.vodimobile.presentation.screens.profile.components.AnotherDataBlock
import com.vodimobile.presentation.screens.profile.components.ExitBlock
import com.vodimobile.presentation.screens.profile.components.PersonalDataCard
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 23.dp),
                title = stringResource(id = R.string.profile_title),
                showBackButton = false,
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
                    profileViewModel.onIntent(ProfileIntent.personalDataClick)
                }
            )
            AnotherDataBlock(
                onRulesClick = {
                    profileViewModel.onIntent(ProfileIntent.rulesClick)
                },
                onFAQClick = {
                    profileViewModel.onIntent(ProfileIntent.faqClick)
                },
                onContactsClick = {
                    profileViewModel.onIntent(ProfileIntent.constantsClick)
                }
            )
            ExitBlock(
                onClick = {
                    profileViewModel.onIntent(ProfileIntent.appExitClick)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ProfileScreenPreview() {
    VodimobileTheme {
        ProfileScreen(profileViewModel = ProfileViewModel())
    }
}
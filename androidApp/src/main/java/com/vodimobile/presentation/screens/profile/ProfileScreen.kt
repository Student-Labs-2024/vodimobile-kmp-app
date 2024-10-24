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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.domain.model.User
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.screens.profile.components.ExitBlock
import com.vodimobile.presentation.screens.profile.components.ProfileItemBlock
import com.vodimobile.presentation.screens.profile.store.ProfileEffect
import com.vodimobile.presentation.screens.profile.store.ProfileIntent
import com.vodimobile.presentation.screens.profile.store.ProfileState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun ProfileScreen(
    onProfileIntent: (ProfileIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") profileEffect: MutableSharedFlow<ProfileEffect>,
    profileState: State<ProfileState>,
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

                ProfileEffect.Auth -> {
                    navHostController.navigate(route = RootScreen.START_SCREEN)
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        onProfileIntent(ProfileIntent.InitUser)
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            onProfileIntent(ProfileIntent.ClearResources)
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
                if (profileState.value.user != User.empty())
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
        val profileViewModel = ProfileViewModel(
            dataStoreStorage = UserDataStoreStorage(
                editUserDataStoreUseCase = EditUserDataStoreUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(LocalContext.current)
                    )
                ),
                getUserDataUseCase = GetUserDataUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                preRegisterUserUseCase = PreRegisterUserUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                editPasswordUseCase = EditPasswordUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                )
            )
        )
        ProfileScreen(
            onProfileIntent = profileViewModel::onIntent,
            profileEffect = profileViewModel.profileEffect,
            profileState = profileViewModel.profileState.collectAsState(),
            navHostController = rememberNavController()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProfileScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        val profileViewModel = ProfileViewModel(
            dataStoreStorage = UserDataStoreStorage(
                editUserDataStoreUseCase = EditUserDataStoreUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(LocalContext.current)
                    )
                ),
                getUserDataUseCase = GetUserDataUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                preRegisterUserUseCase = PreRegisterUserUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                ),
                editPasswordUseCase = EditPasswordUseCase(
                    userDataStoreRepository = UserDataStoreRepositoryImpl(
                        dataStore = getDataStore(
                            LocalContext.current
                        )
                    )
                )
            )
        )
        ProfileScreen(
            onProfileIntent = profileViewModel::onIntent,
            profileEffect = profileViewModel.profileEffect,
            profileState = profileViewModel.profileState.collectAsState(),
            navHostController = rememberNavController()
        )
    }
}

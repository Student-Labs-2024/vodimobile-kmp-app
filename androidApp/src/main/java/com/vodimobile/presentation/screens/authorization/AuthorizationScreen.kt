package com.vodimobile.presentation.screens.authorization

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.App
import com.vodimobile.android.R
import com.vodimobile.data.repository.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.data_store.EditLastAuthUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditTokensUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.EditUserPhoneNumberUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.authorization.components.AuthorizationBlock
import com.vodimobile.presentation.screens.authorization.store.AuthorizationEffect
import com.vodimobile.presentation.screens.authorization.store.AuthorizationIntent
import com.vodimobile.presentation.screens.authorization.store.AuthorizationState
import com.vodimobile.presentation.components.AgreementBlock
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.PasswordValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun AuthorizationScreen(
    onAuthorizationIntent: (AuthorizationIntent) -> Unit,
    authorizationState: State<AuthorizationState>,
    @SuppressLint("ComposeMutableParameters") authorizationEffect: MutableSharedFlow<AuthorizationEffect>,
    navHostController: NavHostController
) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onAuthorizationIntent(AuthorizationIntent.SmsVerification)
        }
    }

    LaunchedEffect(key1 = Unit) {
        authorizationEffect.collect { effect ->
            when (effect) {
                AuthorizationEffect.OpenUserAgreement -> {
                    navHostController.navigate(RegistrationScreens.USER_AGREE_SCREEN)
                }

                AuthorizationEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                AuthorizationEffect.SmsVerification -> {
                    navHostController.navigate(route = "${RegistrationScreens.SMS_VERIFY}/${authorizationState.value.phoneNumber}/${RootScreen.HOME_SCREEN}")
                }

                AuthorizationEffect.AskPermission -> {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            App.INSTANCE,
                            android.Manifest.permission.SEND_SMS
                        ) -> {
                            onAuthorizationIntent(AuthorizationIntent.SmsVerification)
                        }

                        else -> {
                            launcher.launch(android.Manifest.permission.SEND_SMS)
                        }
                    }
                }

                AuthorizationEffect.RememberPassword -> {
                    navHostController.navigate(RegistrationScreens.RESET_PASSWORD_SCREEN)
                }
            }
        }
    }

    val isButtonClicked = remember { mutableStateOf(false) }

    fun resetButtonClicked() {
        if (isButtonClicked.value) isButtonClicked.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 16.dp)
    ) {
        ScreenHeader(
            title = stringResource(
                id = R.string.login_str
            ),
            onNavigateBack = {
                onAuthorizationIntent(AuthorizationIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        AuthorizationBlock(
            authorizationState = authorizationState.value,
            isShowError = isButtonClicked.value,
            onPhoneNumberChanged = {
                onAuthorizationIntent(AuthorizationIntent.PhoneNumberChange(it))
                resetButtonClicked()
            },
            onPasswordChange = {
                onAuthorizationIntent(AuthorizationIntent.PasswordChange(it))
                resetButtonClicked()
            },
            onClickRememberPassword = { onAuthorizationIntent(AuthorizationIntent.RememberPassword) }
        )
        Spacer(modifier = Modifier.height(28.dp))
        AgreementBlock(
            onClickUserAgreement = {
                onAuthorizationIntent(AuthorizationIntent.OpenUserAgreement)
            },
            onClickNextButton = {
                isButtonClicked.value = true
                if (!authorizationState.value.phoneNumberError && !authorizationState.value.passwordError)
                    onAuthorizationIntent(AuthorizationIntent.AskPermission)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthorizationScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val authorizationViewModel = AuthorizationViewModel(
                phoneNumberValidator = PhoneNumberValidator(),
                passwordValidator = PasswordValidator(),
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
                    ),
                    editTokensUseCase = EditTokensUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    ),
                    editLastAuthUseCase = EditLastAuthUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    ),
                    editUserPhoneNumberUseCase = EditUserPhoneNumberUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    )
                )
            )
            AuthorizationScreen(
                onAuthorizationIntent = authorizationViewModel::onIntent,
                authorizationState = authorizationViewModel.authorizationState.collectAsState(),
                authorizationEffect = authorizationViewModel.authorizationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthorizationScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val authorizationViewModel = AuthorizationViewModel(
                phoneNumberValidator = PhoneNumberValidator(),
                passwordValidator = PasswordValidator(),
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
                    ),
                    editTokensUseCase = EditTokensUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    ),
                    editLastAuthUseCase = EditLastAuthUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    ),
                    editUserPhoneNumberUseCase = EditUserPhoneNumberUseCase(
                        userDataStoreRepository = UserDataStoreRepositoryImpl(
                            dataStore = getDataStore(
                                LocalContext.current
                            )
                        )
                    )
                )
            )
            AuthorizationScreen(
                onAuthorizationIntent = authorizationViewModel::onIntent,
                authorizationState = authorizationViewModel.authorizationState.collectAsState(),
                authorizationEffect = authorizationViewModel.authorizationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

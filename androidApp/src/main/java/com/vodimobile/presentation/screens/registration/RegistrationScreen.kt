package com.vodimobile.presentation.screens.registration

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
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import com.vodimobile.domain.use_case.data_store.EditLastAuthUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditTokensUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.components.AgreementBlock
import com.vodimobile.presentation.screens.registration.components.RegistrationBlock
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.NameValidator
import com.vodimobile.presentation.utils.PasswordValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun RegistrationScreen(
    onRegistrationIntent: (RegistrationIntent) -> Unit,
    registrationState: State<RegistrationState>,
    @SuppressLint("ComposeMutableParameters") registrationEffect: MutableSharedFlow<RegistrationEffect>,
    navHostController: NavHostController
) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onRegistrationIntent(RegistrationIntent.SmsVerification)
        }
    }

    LaunchedEffect(key1 = Unit) {
        registrationEffect.collect { effect ->
            when (effect) {
                RegistrationEffect.OpenUserAgreement -> {
                    navHostController.navigate(RegistrationScreens.USER_AGREE_SCREEN)
                }

                RegistrationEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                RegistrationEffect.SmsVerification -> {
                    navHostController.navigate(route = "${RegistrationScreens.SMS_VERIFY}/${registrationState.value.phoneNumber}/${RootScreen.HOME_SCREEN}")
                }

                RegistrationEffect.AskPermission -> {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            App.INSTANCE,
                            android.Manifest.permission.SEND_SMS
                        ) -> {
                            onRegistrationIntent(RegistrationIntent.SmsVerification)
                        }

                        else -> {
                            launcher.launch(android.Manifest.permission.SEND_SMS)
                        }
                    }
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
                id = R.string.title_screen_registration
            ),
            onNavigateBack = {
                onRegistrationIntent(RegistrationIntent.ReturnBack)
            }
        )
        Spacer(modifier = Modifier.height(100.dp))
        RegistrationBlock(
            registrationState = registrationState.value,
            isShowError = isButtonClicked.value,
            onNameChanged = {
                onRegistrationIntent(RegistrationIntent.NameChanged(it))
                resetButtonClicked()
            },
            onPhoneNumberChanged = {
                onRegistrationIntent(RegistrationIntent.PhoneNumberChange(it))
                resetButtonClicked()
            },
            onPasswordChange = {
                onRegistrationIntent(RegistrationIntent.PasswordChange(it))
                resetButtonClicked()
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        AgreementBlock(
            onClickUserAgreement = {
                onRegistrationIntent(RegistrationIntent.OpenUserAgreement)
            },
            onClickNextButton = {
                isButtonClicked.value = true
                if (!registrationState.value.nameError &&
                    !registrationState.value.phoneNumberError &&
                    !registrationState.value.passwordError
                )
                    onRegistrationIntent(RegistrationIntent.AskPermission)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegistrationScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val registrationViewModel = RegistrationViewModel(
                nameValidator = NameValidator(),
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
                    )
                ),
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getTariffListUseCase = GetTariffListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    postNewUserUseCase = PostNewUserUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getAllPlacesUseCase = GetAllPlacesUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    refreshTokenUseCase = RefreshTokenUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getServiceListUseCase = GetServiceListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getBidCostUseCase = GetBidCostUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    )
                )
            )
            RegistrationScreen(
                onRegistrationIntent = registrationViewModel::onIntent,
                registrationState = registrationViewModel.registrationState.collectAsState(),
                registrationEffect = registrationViewModel.registrationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RegistrationScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val registrationViewModel = RegistrationViewModel(
                nameValidator = NameValidator(),
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
                    )
                ),
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getTariffListUseCase = GetTariffListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    postNewUserUseCase = PostNewUserUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getAllPlacesUseCase = GetAllPlacesUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    refreshTokenUseCase = RefreshTokenUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getServiceListUseCase = GetServiceListUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    ),
                    getBidCostUseCase = GetBidCostUseCase(
                        crmRepository = CrmRepositoryImpl(
                            userDataStoreRepository = UserDataStoreRepositoryImpl(
                                dataStore = getDataStore(LocalContext.current)
                            )
                        )
                    )
                )
            )
            RegistrationScreen(
                onRegistrationIntent = registrationViewModel::onIntent,
                registrationState = registrationViewModel.registrationState.collectAsState(),
                registrationEffect = registrationViewModel.registrationEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

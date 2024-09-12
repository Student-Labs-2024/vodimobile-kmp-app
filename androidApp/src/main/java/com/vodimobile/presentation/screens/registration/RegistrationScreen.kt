package com.vodimobile.presentation.screens.registration

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.data.repository.crm.CrmRepositoryImpl
import com.vodimobile.data.repository.supabase.SupabaseRepositoryImpl
import com.vodimobile.domain.storage.crm.CrmStorage
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.storage.supabase.SupabaseStorage
import com.vodimobile.domain.use_case.crm.CreateBidUseCase
import com.vodimobile.domain.use_case.crm.GetAllPlacesUseCase
import com.vodimobile.domain.use_case.crm.GetBidCostUseCase
import com.vodimobile.domain.use_case.crm.GetCarFreeDateRange
import com.vodimobile.domain.use_case.crm.GetCarListUseCase
import com.vodimobile.domain.use_case.crm.GetFreeCarsUseCaSE
import com.vodimobile.domain.use_case.crm.GetServiceListUseCase
import com.vodimobile.domain.use_case.crm.GetTariffListUseCase
import com.vodimobile.domain.use_case.crm.PostNewUserUseCase
import com.vodimobile.domain.use_case.crm.RefreshTokenUseCase
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.HasUserWithPhoneUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCostUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCrmOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateNumberUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceFinishUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceStartUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateServicesUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.block.AgreementBlock
import com.vodimobile.presentation.components.block.ScreenHeader
import com.vodimobile.presentation.screens.registration.components.RegistrationBlock
import com.vodimobile.presentation.screens.registration.store.RegistrationEffect
import com.vodimobile.presentation.screens.registration.store.RegistrationIntent
import com.vodimobile.presentation.screens.registration.store.RegistrationState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.validator.NameValidator
import com.vodimobile.presentation.utils.validator.PasswordValidator
import com.vodimobile.presentation.utils.validator.PhoneNumberValidator
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing", "UnusedMaterial3ScaffoldPaddingParameter")
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
    val snackbarHostState = remember { SnackbarHostState() }

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
                            Manifest.permission.SEND_SMS
                        ) -> {
                            onRegistrationIntent(RegistrationIntent.SmsVerification)
                        }

                        else -> {
                            launcher.launch(Manifest.permission.SEND_SMS)
                        }
                    }
                }

                RegistrationEffect.SupabaseAuthUserError -> {
                    snackbarHostState
                        .showSnackbar(
                            message = App.INSTANCE.resources.getString(R.string.auth_user_supabase_error),
                            duration = SnackbarDuration.Short
                        )
                }

                RegistrationEffect.NotUniquePhone -> {
                    snackbarHostState
                        .showSnackbar(
                            message = App.INSTANCE.resources.getString(R.string.user_phone_not_unique),
                            duration = SnackbarDuration.Short
                        )
                }

                RegistrationEffect.DismissLoadingDialog -> {
                    navHostController.navigateUp()
                }
                RegistrationEffect.ShowLoadingDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }
            }
        }
    }

    val isButtonClicked = remember { mutableStateOf(false) }

    fun resetButtonClicked() {
        if (isButtonClicked.value) isButtonClicked.value = false
    }
    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp),
                title = stringResource(
                    id = R.string.title_screen_registration
                ),
                onNavigateBack = {
                    onRegistrationIntent(RegistrationIntent.ReturnBack)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
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
                },
                enabled = registrationState.value.name.isNotEmpty()
                        && registrationState.value.phoneNumber.isNotEmpty()
                        && registrationState.value.password.isNotEmpty()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegistrationScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )
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
                    )
                ),
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                    getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                    postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                    getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                    refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                    getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                    getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl()),
                    getCarFreeDateRange = GetCarFreeDateRange(crmRepository = CrmRepositoryImpl()),
                    createBidUseCase = CreateBidUseCase(crmRepository = CrmRepositoryImpl())
                ),
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(
                        SupabaseRepositoryImpl(),
                        crmStorage,
                        crmRepository
                    ),
                    updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                    updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                    updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                    updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                    updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                    updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                    updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                    hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
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
            val crmRepository = CrmRepositoryImpl()

            val crmStorage = CrmStorage(
                getCarListUseCase = GetCarListUseCase(crmRepository = crmRepository),
                getTariffListUseCase = GetTariffListUseCase(crmRepository = crmRepository),
                postNewUserUseCase = PostNewUserUseCase(crmRepository = crmRepository),
                getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = crmRepository),
                refreshTokenUseCase = RefreshTokenUseCase(crmRepository = crmRepository),
                getServiceListUseCase = GetServiceListUseCase(crmRepository = crmRepository),
                getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = crmRepository),
                getBidCostUseCase = GetBidCostUseCase(crmRepository = crmRepository),
                getCarFreeDateRange = GetCarFreeDateRange(crmRepository = crmRepository),
                createBidUseCase = CreateBidUseCase(crmRepository = crmRepository)
            )
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
                    )
                ),
                crmStorage = CrmStorage(
                    getCarListUseCase = GetCarListUseCase(crmRepository = CrmRepositoryImpl()),
                    getTariffListUseCase = GetTariffListUseCase(crmRepository = CrmRepositoryImpl()),
                    postNewUserUseCase = PostNewUserUseCase(crmRepository = CrmRepositoryImpl()),
                    getAllPlacesUseCase = GetAllPlacesUseCase(crmRepository = CrmRepositoryImpl()),
                    refreshTokenUseCase = RefreshTokenUseCase(crmRepository = CrmRepositoryImpl()),
                    getServiceListUseCase = GetServiceListUseCase(crmRepository = CrmRepositoryImpl()),
                    getFreeCarsUseCaSE = GetFreeCarsUseCaSE(crmRepository = CrmRepositoryImpl()),
                    getBidCostUseCase = GetBidCostUseCase(crmRepository = CrmRepositoryImpl()),
                    getCarFreeDateRange = GetCarFreeDateRange(crmRepository = CrmRepositoryImpl()),
                    createBidUseCase = CreateBidUseCase(crmRepository = CrmRepositoryImpl())
                ),
                supabaseStorage = SupabaseStorage(
                    getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                    insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                    updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                    updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                    updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                    updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                    insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                    getOrdersUseCase = GetOrdersUseCase(
                        SupabaseRepositoryImpl(),
                        crmStorage,
                        crmRepository
                    ),
                    updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                    updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                    updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                    updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                    updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                    updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                    updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl()),
                    hasUserWithPhoneUseCase = HasUserWithPhoneUseCase(SupabaseRepositoryImpl())
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

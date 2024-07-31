package com.vodimobile.presentation.screens.reset_password

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.components.AgreementBlock
import com.vodimobile.presentation.components.NewPasswordField
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordEffect
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordIntent
import com.vodimobile.presentation.screens.reset_password.store.NewPasswordState
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.PasswordValidator
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun NewPasswordScreen(
    onNewPasswordIntent: (NewPasswordIntent) -> Unit,
    newPasswordState: State<NewPasswordState>,
    @SuppressLint("ComposeMutableParameters") newPasswordEffect: MutableSharedFlow<NewPasswordEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        newPasswordEffect.collect { effect ->
            when (effect) {
                NewPasswordEffect.ReturnBack -> {
                    navHostController.navigateUp()
                }

                NewPasswordEffect.OpenUserAgreement -> {
                    navHostController.navigate(RegistrationScreens.USER_AGREE_SCREEN)
                }

                NewPasswordEffect.SaveData -> {
                    navHostController.navigateUp()
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
            .padding(vertical = 64.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            title = stringResource(
                id = R.string.header_reset_password
            ),
            onNavigateBack = {
                onNewPasswordIntent(NewPasswordIntent.ReturnBack)
            }
        )
        Spacer(Modifier.height(20.dp))
        NewPasswordField(
            modifier = Modifier.padding(top = 8.dp),
            label = stringResource(id = R.string.password_label),
            placeholder = stringResource(id = R.string.create_password_placeholder),
            value = newPasswordState.value.password,
            isError = newPasswordState.value.passwordError && isButtonClicked.value,
            onValueChange = {
                onNewPasswordIntent(NewPasswordIntent.PasswordChange(it))
                resetButtonClicked()
            }
        )
        Spacer(Modifier.height(24.dp))
        AgreementBlock(
            onClickUserAgreement = {
                onNewPasswordIntent(NewPasswordIntent.OpenUserAgreement)
            },
            onClickNextButton = {
                isButtonClicked.value = true
                if (!newPasswordState.value.passwordError)
                    onNewPasswordIntent(NewPasswordIntent.SaveData)
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NewPasswordScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val newPasswordViewModel = NewPasswordViewModel(
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
                )
            )
            NewPasswordScreen(
                onNewPasswordIntent = newPasswordViewModel::onIntent,
                newPasswordState = newPasswordViewModel.newPasswordState.collectAsState(),
                newPasswordEffect = newPasswordViewModel.newPasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewPasswordScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val newPasswordViewModel = NewPasswordViewModel(
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
                )
            )
            NewPasswordScreen(
                onNewPasswordIntent = newPasswordViewModel::onIntent,
                newPasswordState = newPasswordViewModel.newPasswordState.collectAsState(),
                newPasswordEffect = newPasswordViewModel.newPasswordEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

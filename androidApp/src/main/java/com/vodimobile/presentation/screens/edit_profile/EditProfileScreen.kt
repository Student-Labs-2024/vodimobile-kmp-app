package com.vodimobile.presentation.screens.edit_profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.vodimobile.domain.use_case.supabase.order.GetOrdersUseCase
import com.vodimobile.domain.use_case.supabase.GetUserUseCase
import com.vodimobile.domain.use_case.supabase.order.InsertOrderUseCase
import com.vodimobile.domain.use_case.supabase.InsertUserUseCase
import com.vodimobile.domain.use_case.supabase.UpdateFullNameUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePasswordUseCase
import com.vodimobile.domain.use_case.supabase.UpdatePhoneUseCase
import com.vodimobile.domain.use_case.supabase.UpdateTokensUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCostUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateCrmOrderUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateNumberUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateOrderStatusUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceFinishUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdatePlaceStartUseCase
import com.vodimobile.domain.use_case.supabase.order.UpdateServicesUseCase
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.screens.edit_profile.components.ProfileField
import com.vodimobile.presentation.screens.edit_profile.components.VodimobileCenterTopAppBar
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileEffect
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileIntent
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.InputMasks
import com.vodimobile.presentation.utils.PhoneMaskVisualTransformation
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeMutableParameters")
@Composable
fun EditProfileScreen(
    onEditProfileIntent: (EditProfileIntent) -> Unit,
    editProfileState: State<EditProfileState>,
    editProfileEffect: MutableSharedFlow<EditProfileEffect>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val textModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 32.dp, vertical = 12.dp)

    LaunchedEffect(key1 = Unit) {
        editProfileEffect.collect { effect ->
            when (effect) {
                EditProfileEffect.ClickBack -> {
                    navHostController.navigateUp()
                }

                EditProfileEffect.ClickEditPassword -> {
                    navHostController.navigate(route = LeafScreen.CHANGE_PASSWORD_SCREEN)
                }

                is EditProfileEffect.SaveData -> {
                    val result = snackbarHostState
                        .showSnackbar(
                            message = App.INSTANCE.resources.getString(effect.msgResId),
                            actionLabel = if (effect.actionResId > 0) App.INSTANCE.resources.getString(
                                effect.actionResId
                            ) else null,
                            duration = SnackbarDuration.Short
                        )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            onEditProfileIntent(EditProfileIntent.SaveData)
                        }

                        SnackbarResult.Dismissed -> {}
                    }
                }

                EditProfileEffect.ProgressDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }

                EditProfileEffect.RemoveProgressDialog -> {
                    navHostController.navigateUp()
                }
            }
        }
    }

    ExtendedTheme {
        Scaffold(
            topBar = {
                VodimobileCenterTopAppBar(
                    modifier = Modifier.padding(top = 12.dp),
                    onNavBackClick = { onEditProfileIntent(EditProfileIntent.ClickBack) }
                )
            },
            containerColor = ExtendedTheme.colorScheme.onSecondaryBackground,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = modifier
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = ExtendedTheme.colorScheme.containerBack
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp, vertical = 40.dp),
                        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.your_data),
                            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                            modifier = textModifier
                        )

                        ProfileField(
                            text = editProfileState.value.fullName,
                            modifier = textModifier,
                            onValueChange = {
                                onEditProfileIntent(EditProfileIntent.EditFullName(fullName = it))
                            },
                            label = stringResource(id = R.string.full_name),
                            enabled = true
                        )

                        ProfileField(
                            modifier = textModifier,
                            onValueChange = {
                                onEditProfileIntent(EditProfileIntent.EditFullName(fullName = it))
                            },
                            label = stringResource(id = R.string.change_password),
                            trailingIcon = {
                                IconButton(
                                    onClick = { onEditProfileIntent(EditProfileIntent.EditPasswordClick) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = stringResource(id = R.string.change_password)
                                    )
                                }
                            },
                            text = "",
                            enabled = false,
                        )

                        ProfileField(
                            text = editProfileState.value.user.phone,
                            modifier = textModifier,
                            onValueChange = {
                                onEditProfileIntent(EditProfileIntent.EditFullName(fullName = it))
                            },
                            label = stringResource(id = R.string.label_phoneNumber),
                            enabled = false,
                            visualTransformation = PhoneMaskVisualTransformation(InputMasks.RU_PHONE_MASK)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        PrimaryButton(
                            text = stringResource(id = R.string.text_save_button),
                            enabled = !editProfileState.value.isFullNameError,
                            onClick = { onEditProfileIntent(EditProfileIntent.SaveData) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EditProfileScreenDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
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
        val editProfileViewModel = EditProfileViewModel(
            userDataStoreStorage = UserDataStoreStorage(
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
            supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl(), crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )
        )
        EditProfileScreen(
            onEditProfileIntent = editProfileViewModel::onIntent,
            editProfileState = editProfileViewModel.editProfileState.collectAsState(),
            editProfileEffect = editProfileViewModel.editProfileEffect,
            navHostController = rememberNavController()
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun EditProfileScreenLightPreview() {
    VodimobileTheme(dynamicColor = false) {
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
        val editProfileViewModel = EditProfileViewModel(
            userDataStoreStorage = UserDataStoreStorage(
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
            supabaseStorage = SupabaseStorage(
                getUserUseCase = GetUserUseCase(SupabaseRepositoryImpl()),
                insertUserUseCase = InsertUserUseCase(SupabaseRepositoryImpl()),
                updateFullNameUseCase = UpdateFullNameUseCase(SupabaseRepositoryImpl()),
                updatePasswordUseCase = UpdatePasswordUseCase(SupabaseRepositoryImpl()),
                updateTokensUseCase = UpdateTokensUseCase(SupabaseRepositoryImpl()),
                updatePhoneUseCase = UpdatePhoneUseCase(SupabaseRepositoryImpl()),
                insertOrderUseCase = InsertOrderUseCase(SupabaseRepositoryImpl()),
                getOrdersUseCase = GetOrdersUseCase(SupabaseRepositoryImpl(), crmStorage),
                updateOrderStatusUseCase = UpdateOrderStatusUseCase(SupabaseRepositoryImpl()),
                updateNumberUseCase = UpdateNumberUseCase(SupabaseRepositoryImpl()),
                updateCrmOrderUseCase = UpdateCrmOrderUseCase(SupabaseRepositoryImpl()),
                updateServicesUseCase = UpdateServicesUseCase(SupabaseRepositoryImpl()),
                updateCostUseCase = UpdateCostUseCase(SupabaseRepositoryImpl()),
                updatePlaceFinishUseCase = UpdatePlaceFinishUseCase(SupabaseRepositoryImpl()),
                updatePlaceStartUseCase = UpdatePlaceStartUseCase(SupabaseRepositoryImpl())
            )
        )
        EditProfileScreen(
            onEditProfileIntent = editProfileViewModel::onIntent,
            editProfileState = editProfileViewModel.editProfileState.collectAsState(),
            editProfileEffect = editProfileViewModel.editProfileEffect,
            navHostController = rememberNavController()
        )
    }
}

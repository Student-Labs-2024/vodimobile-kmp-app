package com.vodimobile.presentation.screens.logout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.data.data_store.UserDataStoreRepositoryImpl
import com.vodimobile.domain.storage.data_store.UserDataStoreStorage
import com.vodimobile.domain.use_case.data_store.EditPasswordUseCase
import com.vodimobile.domain.use_case.data_store.EditUserDataStoreUseCase
import com.vodimobile.domain.use_case.data_store.GetUserDataUseCase
import com.vodimobile.domain.use_case.data_store.PreRegisterUserUseCase
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.theme.divider
import com.vodimobile.utils.data_store.getDataStore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KFunction1

@Composable
fun LogOutConfirmationDialog(
    onLogOutIntent: (LogOutIntent) -> Unit,
    navHostController: NavHostController,
    @SuppressLint("ComposeMutableParameters") logOutEffect: MutableSharedFlow<LogOutEffect>
) {

    LaunchedEffect(key1 = Unit) {
        logOutEffect.collect { effect ->
            when (effect) {
                LogOutEffect.Cansel -> {
                    navHostController.navigateUp()
                }

                LogOutEffect.LogOut -> {
                    navHostController.navigate(RootScreen.START_SCREEN)
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            onLogOutIntent(LogOutIntent.Cansel)
        },
        title = {
            Text(
                text = stringResource(id = R.string.log_out_confirmation_title),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.log_out_confirmation_body),
                color = MaterialTheme.colorScheme.outline,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.25.sp
            )
        },
        buttons = {
            Divider(
                color = divider,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    onLogOutIntent(LogOutIntent.Cansel)
                }) {
                    Text(
                        text = stringResource(id = R.string.confirmation_cancel_button),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    )
                }
                TextButton(onClick = {
                    onLogOutIntent(LogOutIntent.LogOut)
                }) {
                    Text(
                        text = stringResource(id = R.string.confirmation_logout_button),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.1.sp
                    )
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .width(IntrinsicSize.Max),
        backgroundColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview
@Composable
private fun LogOutConfirmationDialogPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            val logOutViewModel = LogOutViewModel(
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
                )
            )
            LogOutConfirmationDialog(
                onLogOutIntent = {},
                navHostController = rememberNavController(),
                logOutEffect = logOutViewModel.logOutEffect
            )
        }
    }
}

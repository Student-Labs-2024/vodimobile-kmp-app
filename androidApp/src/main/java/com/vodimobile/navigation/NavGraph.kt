package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vodimobile.android.R
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafHomeScreen
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.components.ProgressDialogIndicator
import com.vodimobile.presentation.screens.change_password.ChangePasswordScreen
import com.vodimobile.presentation.screens.change_password.ChangePasswordViewModel
import com.vodimobile.presentation.screens.contact.ContactScreen
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.date_setect.DateSelectDialog
import com.vodimobile.presentation.screens.edit_profile.EditProfileScreen
import com.vodimobile.presentation.screens.edit_profile.EditProfileViewModel
import com.vodimobile.presentation.screens.faq.FaqScreen
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.home.HomeViewModel
import com.vodimobile.presentation.screens.home.store.HomeState
import com.vodimobile.presentation.screens.home.HomeScreen
import com.vodimobile.presentation.screens.logout.LogOutConfirmationDialog
import com.vodimobile.presentation.screens.network_error.ConnectionErrorScreen
import com.vodimobile.presentation.screens.network_error.ConnectionErrorViewModel
import com.vodimobile.presentation.screens.orders.OrdersScreen
import com.vodimobile.presentation.screens.profile.ProfileScreen
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.registration.RegistrationScreen
import com.vodimobile.presentation.screens.registration.RegistrationViewModel
import com.vodimobile.presentation.screens.rule_details.RuleDetailsScreen
import com.vodimobile.presentation.screens.rule_details.RuleDetailsViewModel
import com.vodimobile.presentation.screens.rules.RuleScreen
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.screens.sms.SmsScreen
import com.vodimobile.presentation.screens.sms.SmsViewModel
import com.vodimobile.presentation.screens.start_screen.StartScreen
import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import com.vodimobile.presentation.screens.user_agreement.UserAgreementScreen
import com.vodimobile.presentation.screens.user_agreement.UserAgreementViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = RootScreen.HOME_SCREEN
    ) {
        navigation(
            route = RootScreen.HOME_SCREEN,
            startDestination = LeafHomeScreen.NO_INTERNET_SCREEN
        ) {
            composable(
                route = LeafHomeScreen.HOME_SCREEN,
                arguments = listOf(
                    navArgument(name = "selected-date") {
                        type = NavType.LongType
                        defaultValue = 0L
                    },
                )
            ) { backStackEntry ->
                val selectedDate = backStackEntry.savedStateHandle.getStateFlow(
                    "selected-date",
                    initialValue = 0L,
                ).collectAsState().value
                val homeViewModel: HomeViewModel = koinViewModel()
                HomeScreen(
                    homeState = homeViewModel.homeState.collectAsState(
                        initial = HomeState(
                            selectedDate = selectedDate
                        )
                    ),
                    homeEffect = homeViewModel.homeEffect,
                    onHomeIntent = homeViewModel::onIntent,
                    navHostController = navHostController,
                    selectedDate = selectedDate,
                    modifier = modifier
                )
            }
            dialog(
                route = DialogIdentifiers.DATE_SELECT_DIALOG
            ) { backEntry ->
                var selectedDate by remember { mutableStateOf(0L) }
                DateSelectDialog(
                    onDismissClick = { navHostController.navigateUp() },
                    onConfirmClick = { value ->
                        navHostController.previousBackStackEntry?.savedStateHandle?.set(
                            "selected-date",
                            value,
                        )
                        selectedDate = value
                        navHostController.navigateUp()
                    },
                    initialDateInMillis = if (selectedDate == 0L) System.currentTimeMillis() else selectedDate
                )
            }
            composable(
                route = "${RegistrationScreens.SMS_VERIFY}/{phone}",
                arguments = listOf(navArgument("phone") { type = NavType.StringType })
            ) { backStackEntry ->
                val smsViewModel: SmsViewModel = koinViewModel()

                SmsScreen(
                    smsState = smsViewModel.smsState.collectAsState(),
                    smsEffect = smsViewModel.smsEffect,
                    phone = backStackEntry.arguments?.getString("phone") ?: "",
                    onIntent = smsViewModel::onIntent,
                    navHostController = navHostController
                )
            }
            composable(route = LeafHomeScreen.NO_INTERNET_SCREEN) {
                val connectionErrorViewModel: ConnectionErrorViewModel = koinViewModel()
                ConnectionErrorScreen(
                    onNetworkErrorIntent = connectionErrorViewModel::onIntent,
                    networkErrorEffect = connectionErrorViewModel.connectionErrorEffect,
                    navHostController = navHostController
                )
            }
        }
        composable(RootScreen.ORDERS_SCREEN) {
            OrdersScreen()
        }
        navigation(
            route = RootScreen.PROFILE_SCREEN,
            startDestination = LeafScreen.PROFILE_SCREEN
        ) {
            composable(route = LeafScreen.PROFILE_SCREEN) {
                val profileViewModel: ProfileViewModel =
                    koinViewModel()

                ProfileScreen(
                    onProfileIntent = profileViewModel::onIntent,
                    profileEffect = profileViewModel.profileEffect,
                    navHostController = navHostController
                )
            }
            composable(route = LeafScreen.RULES_SCREEN) {
                val rulesViewModel: RulesViewModel = koinViewModel()

                RuleScreen(
                    onRulesIntent = rulesViewModel::onIntent,
                    rulesEffect = rulesViewModel.rulesEffect,
                    rulesState = rulesViewModel.rulesState.collectAsState(),
                    navHostController = navHostController
                )
            }
            composable(
                route = "${LeafScreen.RULE_DETAILS_SCREEN}/{ruleId}",
                arguments = listOf(
                    navArgument(name = "ruleId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val ruleId = backStackEntry.arguments?.getInt("ruleId")!!
                val ruleDetailsViewModel: RuleDetailsViewModel = koinViewModel()

                RuleDetailsScreen(
                    onRuleDetailsIntent = ruleDetailsViewModel::onIntent,
                    ruleDetailsEffect = ruleDetailsViewModel.rulesDetailsEffect,
                    ruleDetailsState = ruleDetailsViewModel.ruleDetailsState.collectAsState(),
                    navHostController = navHostController,
                    ruleId = ruleId
                )
            }
            dialog(route = DialogIdentifiers.LOG_OUT_DIALOG) {
                LogOutConfirmationDialog(
                    onDismiss = { navHostController.navigateUp() },
                    onConfirm = { navHostController.navigate(RootScreen.START_SCREEN) })
            }
            composable(route = LeafScreen.FAQ_SCREEN) {
                val faqViewModel: FaqViewModel = koinViewModel()

                FaqScreen(
                    onFaqIntent = faqViewModel::onIntent,
                    faqEffect = faqViewModel.faqEffect,
                    faqState = faqViewModel.faqState.collectAsState(),
                    navHostController = navHostController
                )
            }
            composable(route = LeafScreen.CONTACTS_SCREEN) {
                val contactViewModel: ContactViewModel = koinViewModel()

                ContactScreen(
                    onContactIntent = contactViewModel::onIntent,
                    contactEffect = contactViewModel.contactEffect,
                    contactState = contactViewModel.contactState.collectAsState(),
                    validYear = contactViewModel.getValidYear(startYear = stringResource(id = R.string.version_year_str)),
                    navHostController = navHostController
                )
            }
            composable(route = LeafScreen.EDIT_PROFILE) {
                val editProfileViewModel: EditProfileViewModel = koinViewModel()
                EditProfileScreen(
                    modifier = modifier,
                    onEditProfileIntent = editProfileViewModel::onIntent,
                    editProfileState = editProfileViewModel.editProfileState.collectAsState(),
                    editProfileEffect = editProfileViewModel.editProfileEffect,
                    navHostController = navHostController
                )
            }
            composable(route = LeafScreen.CHANGE_PASSWORD_SCREEN) {
                val changePasswordViewModel: ChangePasswordViewModel = koinViewModel()
                ChangePasswordScreen(
                    onChangePasswordIntent = changePasswordViewModel::onIntent,
                    oldPasswordState = changePasswordViewModel.oldPasswordState.collectAsState(),
                    newPasswordState = changePasswordViewModel.newPasswordState.collectAsState(),
                    changePasswordEffect = changePasswordViewModel.changePasswordEffect,
                    navHostController = navHostController
                )
            }
            dialog(route = DialogIdentifiers.LOADING_DIALOG) {
                ProgressDialogIndicator()
            }
        }
        navigation(
            route = RootScreen.START_SCREEN,
            startDestination = RegistrationScreens.START_SCREEN
        ) {
            composable(route = RegistrationScreens.START_SCREEN) {
                val startScreenViewModel: StartScreenViewModel = koinViewModel()

                StartScreen(
                    onStartScreenIntent = startScreenViewModel::onIntent,
                    startScreenEffect = startScreenViewModel.startScreenEffect,
                    navHostController = navHostController
                )
            }
            composable(route = RegistrationScreens.REGISTRATION_SCREEN) {
                val registrationViewModel: RegistrationViewModel = koinViewModel()

                RegistrationScreen(
                    onRegistrationIntent = registrationViewModel::onIntent,
                    registrationState = registrationViewModel.registrationState.collectAsState(),
                    registrationEffect = registrationViewModel.registrationEffect,
                    navHostController = navHostController
                )
            }
            composable(route = RegistrationScreens.USER_AGREE_SCREEN) {
                val userAgreementViewModel: UserAgreementViewModel = koinViewModel()
                UserAgreementScreen(
                    onUserAgreementIntent = userAgreementViewModel::onIntent,
                    userAgreementEffect = userAgreementViewModel.userAgreementEffect,
                    navHostController = navHostController
                )
            }
            composable(
                route = RegistrationScreens.SMS_VERIFY,
                arguments = listOf(navArgument("phone") { type = NavType.StringType })
            ) { backStackEntry ->
                val smsViewModel: SmsViewModel = koinViewModel()

                SmsScreen(
                    smsState = smsViewModel.smsState.collectAsState(),
                    smsEffect = smsViewModel.smsEffect,
                    phone = backStackEntry.arguments?.getString("phone") ?: "",
                    onIntent = smsViewModel::onIntent,
                    navHostController = navHostController
                )
            }
        }
    }
}

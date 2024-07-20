package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.screens.home.HomeScreen
import com.vodimobile.presentation.screens.logout.LogOutConfirmationDialog
import com.vodimobile.presentation.screens.orders.OrdersScreen
import com.vodimobile.presentation.screens.profile.ProfileScreen
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.rule_details.RuleDetailsScreen
import com.vodimobile.presentation.screens.rule_details.RuleDetailsViewModel
import com.vodimobile.presentation.screens.rules.RuleScreen
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.screens.start_screen.StartScreen
import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import com.vodimobile.presentation.screens.contact.ContactScreen
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.faq.FaqScreen
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.registration.RegistrationScreen
import com.vodimobile.presentation.screens.registration.RegistrationViewModel
import com.vodimobile.presentation.screens.user_agreement.UserAgreementScreen
import com.vodimobile.presentation.screens.user_agreement.UserAgreementViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = RootScreen.HOME_SCREEN
    ) {
        composable(RootScreen.HOME_SCREEN) {
            HomeScreen()
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
        }
    }
}

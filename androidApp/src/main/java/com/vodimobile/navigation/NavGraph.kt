package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.domain.model.RulesAndConditionModel
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RootScreen
import com.vodimobile.presentation.screens.home.HomeScreen
import com.vodimobile.presentation.screens.logout.LogOutConfirmationDialog
import com.vodimobile.presentation.screens.orders.OrdersScreen
import com.vodimobile.presentation.screens.profile.ProfileScreen
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.rule_details.RuleDetailsScreen
import com.vodimobile.presentation.screens.rule_details.RulesDetailsViewModel
import com.vodimobile.presentation.screens.rules.RuleScreen
import com.vodimobile.presentation.screens.rules.RuleViewModel
import com.vodimobile.presentation.screens.startscreen.StartScreen
import com.vodimobile.presentation.screens.startscreen.StartScreenViewModel
import org.koin.core.parameter.parametersOf
import com.vodimobile.presentation.screens.contact.ContactScreen
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.contact.store.ContactOutput
import com.vodimobile.presentation.screens.faq.FaqScreen
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.faq.store.FaqOutput
import com.vodimobile.presentation.screens.profile.store.ProfileOutput
import com.vodimobile.presentation.screens.rule_details.store.RulesDetailsOutput
import com.vodimobile.presentation.screens.rules.store.RulesOutput
import com.vodimobile.presentation.screens.startscreen.store.StartScreenOutput
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    val rulesAndConditionList: List<RulesAndConditionModel> =
        RulesAndConditionModel.getRulesAndConditionModelList(
            LocalContext.current.resources
        )

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
                val profileOutput = { out: ProfileOutput ->
                    when (out) {
                        ProfileOutput.AppExitClick -> {
                            navHostController.navigate(route = DialogIdentifiers.LOG_OUT_DIALOG)
                        }

                        ProfileOutput.ConstantsClick -> {
                            navHostController.navigate(route = LeafScreen.CONTACTS_SCREEN)
                        }

                        ProfileOutput.FaqClick -> {
                            navHostController.navigate(route = LeafScreen.FAQ_SCREEN)
                        }

                        ProfileOutput.PersonalDataClick -> {

                        }

                        ProfileOutput.RulesClick -> {
                            navHostController.navigate(route = LeafScreen.RULES_SCREEN)
                        }
                    }
                }
                val profileViewModel: ProfileViewModel =
                    koinViewModel { parametersOf(profileOutput) }

                ProfileScreen(profileViewModel = profileViewModel)
            }
            composable(route = LeafScreen.RULES_SCREEN) {
                val rulesOutput = { out: RulesOutput ->
                    when (out) {
                        RulesOutput.BackClick -> {
                            navHostController.navigateUp()
                        }

                        is RulesOutput.RuleClick -> {
                            navHostController.navigate("${LeafScreen.RULE_DETAILS_SCREEN}/${out.ruleId}")
                        }
                    }
                }
                val rulesViewModel: RuleViewModel =
                    koinViewModel { parametersOf(rulesOutput) }

                RuleScreen(viewModel = rulesViewModel, rules = rulesAndConditionList)
            }
            composable(
                route = "${LeafScreen.RULE_DETAILS_SCREEN}/{ruleId}",
                arguments = listOf(
                    navArgument(name = "ruleId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val rulesDetailsOutput = { out: RulesDetailsOutput ->
                    when (out) {
                        RulesDetailsOutput.ReturnBack -> {
                            navHostController.navigateUp()
                        }
                    }
                }
                val ruleId = backStackEntry.arguments?.getInt("ruleId")!!
                val rulesDetailsViewModel: RulesDetailsViewModel =
                    koinViewModel { parametersOf(rulesDetailsOutput) }

                RuleDetailsScreen(
                    viewModel = rulesDetailsViewModel,
                    ruleId = ruleId,
                    rules = rulesAndConditionList
                )
            }
            dialog(route = DialogIdentifiers.LOG_OUT_DIALOG) {
                LogOutConfirmationDialog(
                    onDismiss = { navHostController.navigateUp() },
                    onConfirm = { navHostController.navigate(RootScreen.START_SCREEN) })
            }
            composable(route = LeafScreen.FAQ_SCREEN) {
                val faqOutput = { out: FaqOutput ->
                    when (out) {
                        FaqOutput.BackClick -> {
                            navHostController.navigateUp()
                        }
                    }
                }
                val faqViewModel: FaqViewModel = koinViewModel { parametersOf(faqOutput) }

                FaqScreen(faqViewModel = faqViewModel)
            }
            composable(route = LeafScreen.CONTACTS_SCREEN) {
                val contactOutput = { out: ContactOutput ->
                    when (out) {
                        ContactOutput.BackClick -> {
                            navHostController.navigateUp()
                        }

                        ContactOutput.TelegramClick -> {}
                        ContactOutput.VkClick -> {}
                        ContactOutput.WhatsappClick -> {}
                    }
                }
                val contactViewModel: ContactViewModel =
                    koinViewModel { parametersOf(contactOutput) }

                ContactScreen(сontactViewModel = contactViewModel)
            }
        }
        composable(route = RootScreen.START_SCREEN) {
            val startScreenOut = { out: StartScreenOutput ->
                when (out) {
                    StartScreenOutput.ClickLogin -> {}
                    StartScreenOutput.ClickRegistration -> {}
                    StartScreenOutput.CloseClick -> {
                        navHostController.navigateUp()
                    }
                }
            }
            val startScreenViewModel: StartScreenViewModel =
                koinViewModel { parametersOf(startScreenOut) }
            StartScreen(startScreenViewModel = startScreenViewModel)
        }
    }
}

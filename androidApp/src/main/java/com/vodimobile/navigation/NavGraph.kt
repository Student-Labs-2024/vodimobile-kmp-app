package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
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

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val rulesAndConditionList: List<RulesAndConditionModel> = RulesAndConditionModel.getRulesAndConditionModelList(
        LocalContext.current.resources)

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
                ProfileScreen(profileViewModel = ProfileViewModel(navHostController))
            }
            composable(route = LeafScreen.RULES_SCREEN) {
                RuleScreen(viewModel = RuleViewModel(navHostController), rules = rulesAndConditionList)
            }
            dialog(route = DialogIdentifiers.LOG_OUT_DIALOG) {
                LogOutConfirmationDialog(
                    onDismiss = { navHostController.navigateUp() },
                    onConfirm = { navHostController.navigate(RootScreen.START_SCREEN) })
            }
            composable("${LeafScreen.RULE_DETAILS_SCREEN}/{ruleId}") { backStackEntry ->
                val ruleId = backStackEntry.arguments?.getInt("ruleId")!!
                RuleDetailsScreen(
                    viewModel = RulesDetailsViewModel(navHostController),
                    ruleId = ruleId,
                    rules = rulesAndConditionList
                )
            }
        }
        composable(route = RootScreen.START_SCREEN) {
            StartScreen(startScreenViewModel = StartScreenViewModel())
        }
    }
}

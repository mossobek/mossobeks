package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.presentation.allReportsList.AllReportsListScreen
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportUserOrdersListScreen


fun NavGraphBuilder.reportsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.REPORTS,
        startDestination = ReportsScreens.ReportUserOrdersListScreen.route + "/{id}/{username}"
    ) {
        composable(
            route = ReportsScreens.ReportUserOrdersListScreen.route + "/{id}/{username}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: Constants.NO_VALUE
            val username = backStackEntry.arguments?.getString("username") ?: Constants.NO_VALUE
            ReportUserOrdersListScreen(
                username = username,
                id = id,
                navBack = {
                    navController.popBackStack()
                },
                navigateToOrderScreen = {

                })
        }
        composable(
            route = ReportsScreens.AllReportsList.route
        ) {
            AllReportsListScreen(
                navBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}


sealed class ReportsScreens(val route: String) {

    object ReportSearch : ReportsScreens(route = "SEARCH")
    object AllReportsList : ReportsScreens(route = "ALL_REPORT_LIST")
    object ReportUserOrdersListScreen : ReportsScreens(route = "REPORTS_USER_ORDER_LIST")


}



package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.reportsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.REPORTS, startDestination = ReportsScreens.ReportSearch.route
    ) {
        composable(route = ReportsScreens.ReportSearch.route) {
//            ReportsSearchScreen(
//                navToOrder = { order ->
//
//                }, navBack = {
//
//                }
//            )
        }
    }

}

sealed class ReportsScreens(val route: String) {

    object ReportSearch : ReportsScreens(route = "SEARCH")
    object ReportOrderDetails : ReportsScreens(route = "REPORTS_ORDER_DETAILS")

}



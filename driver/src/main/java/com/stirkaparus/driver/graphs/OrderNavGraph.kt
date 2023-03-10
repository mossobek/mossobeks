package com.stirkaparus.driver.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.driver.common.Constants.NO_VALUE
import com.stirkaparus.driver.presentation.carpets.CarpetsScreen
import com.stirkaparus.driver.presentation.order_details.OrderDetailsScreen

fun NavGraphBuilder.orderNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.ORDER_DETAIL + "/{id}",
        startDestination = OrderDetailsScreen.Details.route + "/{id}"
    ) {
        composable(
            route = OrderDetailsScreen.Details.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: NO_VALUE
            OrderDetailsScreen(
                id = id,
                navBack = {
                    navController.popBackStack()
                },
                navToCarpets = {
                    navController.navigate(OrderDetailsScreen.Carpets.route + "/$id")
                },
            )
        }
        composable(route = OrderDetailsScreen.Carpets.route + "/{id}") {
            CarpetsScreen(

            )
        }
    }
}


sealed class OrderDetailsScreen(val route: String) {

    object Details : OrderDetailsScreen(route = "CREATED_ORDER_DETAIL")
    object Carpets : OrderDetailsScreen(route = "CARPETS")
}
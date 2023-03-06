package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.common.Constants.NO_VALUE
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsScreen
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsScreen
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrderDetailsScreen

fun NavGraphBuilder.orderNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.ORDER_DETAIL + "/{id}",
        startDestination = OrderDetailsScreen.OrderDetails.route + "/{id}"
    ) {
        composable(
            route = OrderDetailsScreen.OrderDetails.route + "/{id}",
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
            CarpetsScreen(navController = navController)
        }
    }
}
package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsScreen
import com.stirkaparus.stirkaparus.presentation.order_details_screen.OrderDetailsScreen
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.OrderEditScreen
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrderDetailsScreen1

fun NavGraphBuilder.orderNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.ORDER_DETAIL + "/{id}",
        startDestination = OrderDetailsScreen1.OrderDetails.route + "/{id}"
    ) {
        composable(
            route = OrderDetailsScreen1.OrderDetails.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {

            OrderDetailsScreen(navController = navController)
        }
        composable(route = OrderDetailsScreen1.Edit1.route + "/{id}") {
            OrderEditScreen(navController = navController)
        }
        composable(route = OrderDetailsScreen1.Carpets.route + "/{id}") {
            CarpetsScreen(navController = navController)
        }
    }
}
package com.stirkaparus.driver.graphs

import android.content.ContentValues.TAG
import android.util.Log
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.driver.presentation.orderDetails.OrderDetailsScreen

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
        ) {
            Log.e(TAG, "createdOrderNavGraph: ")
            OrderDetailsScreen(navController = navController)
        }

    }
}


sealed class OrderDetailsScreen(val route: String) {

    object Details : OrderDetailsScreen(route = "CREATED_ORDER_DETAIL")
    object Carpets : OrderDetailsScreen(route = "CARPETS")
}
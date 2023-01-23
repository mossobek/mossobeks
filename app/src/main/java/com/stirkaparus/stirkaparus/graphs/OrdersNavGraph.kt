package com.stirkaparus.stirkaparus.graphs

import android.content.ContentValues.TAG
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.stirkaparus.screens.order_details_screen.OrderDetailsScreen
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrdersScreen

//fun NavGraphBuilder.orderDetailsGraph(
//    navController: NavHostController
//) {
//    navigation(
//        route = Graph.ORDERS,
//        startDestination = OrderDetailsScreen.Orders.route
//    ) {
//        Log.e(TAG, "orderDetailsGraph: $route")
//        composable(route = OrderDetailsScreen.Orders.route) {
//
//            OrdersScreen(navController = navController)
//
//
//        }
//
//        composable(route = OrderDetailsScreen.Details.route) {
//
//            OrderDetailsScreen(navController = navController)
//
//
//        }
//
//
//    }
//}
//
//sealed class OrderDetailsScreen(val route: String) {
//    object Orders : OrderDetailsScreen(route = "ORDERS")
//    object Details : OrderDetailsScreen(route = "DETAILS")
//    object Edit : OrderDetailsScreen(route = "EDIT")
//}
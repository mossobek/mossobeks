package com.stirkaparus.driver.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stirkaparus.driver.presentation.BottomBarScreen
import com.stirkaparus.driver.presentation.addOrder.AddOrderScreen
import com.stirkaparus.driver.presentation.allOrders.AllOrders
import com.stirkaparus.driver.presentation.orders.CreatedOrders
import com.stirkaparus.driver.presentation.orders.FinishedOrders
import com.stirkaparus.driver.presentation.report.ReportsScreen


@Composable
fun HomeNavGraph(navController: NavHostController, paddingValue: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Orders.route
    ) {

        composable(route = BottomBarScreen.Orders.route) {
           AllOrders(navController = navController, paddingValue)

        }

        composable(route = BottomBarScreen.Add.route) {

            AddOrderScreen(navController = navController, paddingValue)

        }
        composable(route = BottomBarScreen.Reports.route) {

            ReportsScreen(navController = navController, paddingValue)

        }
        orderNavGraph(navController = navController)

    }
}
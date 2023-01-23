package com.stirkaparus.stirkaparus.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.stirkaparus.BottomBarScreen
import com.stirkaparus.stirkaparus.screens.ScreenContent
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrdersScreen
import com.stirkaparus.stirkaparus.screens.orders_list_screen.orderNavGraph


@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
            OrdersScreen(navController = navController)
//            ScreenContent(name = BottomBarScreen.Home.route,
//                 onClick = {
//                 navController.navigate(Graph.DETAILS)
//             })
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(name = BottomBarScreen.Profile.route, onClick = { })
        }
        composable(route = BottomBarScreen.Orders.route) {

            OrdersScreen(navController = navController)

        }
        composable(route = BottomBarScreen.Add.route) {

            AddScreen()

        }

        detailsNavGraph(navController = navController)
        orderNavGraph(navController = navController)


    }
}


fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route, inclusive = false
                )
            }
        }
    }
}


sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}
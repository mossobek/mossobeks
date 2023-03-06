package com.stirkaparus.stirkaparus.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.BottomBarScreen
import com.stirkaparus.stirkaparus.presentation.DashScreen
import com.stirkaparus.stirkaparus.presentation.add_order_screen.AddScreen
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrderDetailsScreen
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrdersScreen
import com.stirkaparus.stirkaparus.presentation.profile.ProfileScreen
import com.stirkaparus.stirkaparus.presentation.reports.ReportsScreen


@Composable
fun HomeNavGraph(navController: NavHostController, paddingValue: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {


        composable(route = BottomBarScreen.Home.route) {
            DashScreen(navController = navController, paddingValue)
        }

        composable(route = BottomBarScreen.Orders.route) {

            OrdersScreen(
                navController = navController,
                paddingValue,
                navigateToUserOrdersScreen = { userId ->
                    navController.navigate("${OrderDetailsScreen.OrderDetails.route}/${userId}")
                })

        }
        composable(route = BottomBarScreen.Add.route) {

            AddScreen(navController = navController, paddingValue)

        }
        composable(route = BottomBarScreen.Reports.route) {
            ReportsScreen(
                navController = navController,
                bottomPadding = paddingValue,
                navigateToUserOrdersScreen = { userId, username ->
                    navController.navigate("${ReportsScreens.ReportUserOrdersListScreen.route}/${userId}/${username}")
                },

                )

        }
        composable(route = BottomBarScreen.Profile.route) {

            ProfileScreen(paddingValue,
                navToAddUserScreen = {
                    navController.navigate(ProfileScreens.AddNewUser.route)
                })

        }



        orderNavGraph(navController = navController)
        profileNavGraph(navController = navController)
        reportsNavGraph(navController = navController)

    }
}

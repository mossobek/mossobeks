package com.stirkaparus.driver.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stirkaparus.driver.presentation.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen(
                navigateToProfileVerifyScreen = {
                    navController.navigate(AuthScreen.UserActivation.route) {
                        popUpTo(Graph.ROOT) {
                            inclusive = false
                        }
                    }
                }
            )

        }

    }

}
fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}
object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val ORDER_DETAIL = "created_order_detail_graph"
}
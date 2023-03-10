package com.stirkaparus.driver.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stirkaparus.driver.presentation.HomeScreen
import com.stirkaparus.driver.presentation.splash.SplashScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.SPLASH) {

            SplashScreen(
                navToUserActivationScreen = {
                    navController.navigate(AuthScreen.UserActivation.route) {
                        navController.popBackStack(
                            0, false
                        )
                    }

                },
                navToHomeScreen = {
                    navController.navigate(Graph.HOME){
                        popUpTo(Graph.ROOT) {
                            inclusive = false
                        }
                    }

                }
            )
        }
        composable(route = Graph.HOME) {
            HomeScreen(
                navToUpdateScreen = {
                    navController.navigate(AuthScreen.Update.route) {
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
    const val SPLASH = "splash_graph"
    const val ORDER_DETAIL = "created_order_detail_graph"
    const val USER_DETAIL = "user_detail_graph"
}
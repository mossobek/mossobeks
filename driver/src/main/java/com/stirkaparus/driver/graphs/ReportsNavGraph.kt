package com.stirkaparus.driver.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.driver.presentation.user_profile.UserProfileScreen


fun NavGraphBuilder.reportsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.USER_DETAIL,
        startDestination = ProfileScreen.UserProfileScreen.route
    ) {
        composable(
            route = ProfileScreen.UserProfileScreen.route
        ) {
            UserProfileScreen(navBack = {
                navController.popBackStack()
            })
        }

    }
}


sealed class ProfileScreen(val route: String) {

    object UserProfileScreen : ProfileScreen(route = "USER_PROFILE")
    object Carpets : ProfileScreen(route = "CARPETS")
}


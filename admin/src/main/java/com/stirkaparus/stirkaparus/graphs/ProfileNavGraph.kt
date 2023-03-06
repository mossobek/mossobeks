package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.stirkaparus.presentation.add_new_user.AddNewUserScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreens.AddNewUser.route
    ) {

        composable(route = ProfileScreens.AddNewUser.route) {
            AddNewUserScreen(navController = navController, navBack = {
                 navController.popBackStack()
            })
        }

    }
}

sealed class ProfileScreens(val route: String) {

    object AddNewUser : ProfileScreens(route = "ADD_NEW_USER")
    object Profile : ProfileScreens(route = "PROFILE")

}

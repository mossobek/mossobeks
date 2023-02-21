package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.presentation.add_new_user.AddNewUserScreen
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsScreen

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

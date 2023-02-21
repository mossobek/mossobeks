package com.stirkaparus.stirkaparus.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.stirkaparus.presentation.forgotPassword.ForgotPasswordScreen
import com.stirkaparus.stirkaparus.presentation.login.LoginScreen
import com.stirkaparus.stirkaparus.presentation.notAdmin.NotAdminScreen
import com.stirkaparus.stirkaparus.presentation.signUp.SignUpScreen
import com.stirkaparus.stirkaparus.presentation.user_details.UserDetailsScreen
import com.stirkaparus.stirkaparus.presentation.verify_email.VerifyEmailScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
            navController.popBackStack(
                0, false
            )
        }
        composable(route = AuthScreen.UserActivation.route) {
            UserDetailsScreen(
                navController = navController,
                navigateToMainScreen = {
                    navController.navigate(Graph.HOME)
                    navController.popBackStack(
                        0, false
                    )
                })

        }




        composable(route = AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController, navigateBack = {
                navController.popBackStack()
            })

        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(navController = navController, navigateBack = {
                navController.popBackStack()
            })

        }
        composable(route = AuthScreen.Verify.route) {
            VerifyEmailScreen(navigateToProfileScreen = {
                navController.navigate(Graph.HOME)
            })

        }

    }
}

sealed class AuthScreen(val route: String) {

    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SING_UP")
    object UserActivation : AuthScreen(route = "USER_ACTIVATION")
    object ForgotPassword : AuthScreen(route = "FORGOT_PASSWORD")
     object Verify : AuthScreen(route = "VERIFY")

}
package com.stirkaparus.driver.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.stirkaparus.driver.presentation.UpdateScreen
import com.stirkaparus.driver.presentation.forgot_password.ForgotPasswordScreen
import com.stirkaparus.driver.presentation.login.LoginScreen
import com.stirkaparus.driver.presentation.user_details.UserDetailsScreen
import com.stirkaparus.driver.presentation.sign_up.SignUpScreen
import com.stirkaparus.driver.presentation.verify_email.VerifyEmailScreen

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

        composable(route = AuthScreen.Update.route) {
            UpdateScreen()

        }
        composable(route = AuthScreen.UserActivation.route) {
            UserDetailsScreen(
                navController = navController,
                navigateToMainScreen = {
                    navController.navigate(Graph.SPLASH)
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
                navController.navigate(Graph.SPLASH)
            })

        }

    }
}


sealed class AuthScreen(val route: String) {
    object Update : AuthScreen(route = "UPDATE")
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SING_UP")
    object Verify : AuthScreen(route = "VERIFY")
    object ForgotPassword : AuthScreen(route = "FORGOT_PASSWORD")
    object UserActivation : AuthScreen(route = "USER_ACTIVATION")

}






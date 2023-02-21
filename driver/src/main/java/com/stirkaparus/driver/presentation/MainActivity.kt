package com.stirkaparus.driver.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.stirkaparus.driver.graphs.AuthScreen
import com.stirkaparus.driver.graphs.Graph
import com.stirkaparus.driver.graphs.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            RootNavigationGraph(navController = navController)
            AuthState()
        }
    }





    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {
                NavigateToMainScreen()
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    fun NavigateToSignInScreen() = navController.navigate(Graph.AUTHENTICATION) {
        popUpTo(navController.graph.id)
        {
            inclusive = true

        }
    }

    @Composable
    private fun NavigateToMainScreen() = navController.navigate(Graph.HOME)
    {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() =
        navController.navigate(AuthScreen.Verify.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }

}


package com.stirkaparus.driver.presentation.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.driver.common.showToast
import com.stirkaparus.driver.graphs.AuthScreen
import com.stirkaparus.driver.presentation.login.components.LoginContent
import com.stirkaparus.driver.presentation.login.components.LoginTopBar
import com.stirkaparus.driver.presentation.login.components.SignIn

@SuppressLint(
    "FlowOperatorInvokedInComposition", "CoroutineCreationDuringComposition",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current


    Scaffold(
        topBar = { LoginTopBar() },
        content = { padding ->
            LoginContent(
                padding = padding,
                signIn = { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password)
                },
                navigateToForgotPasswordScreen = {
                    navController.navigate(AuthScreen.ForgotPassword.route)
                },
                navigateToSignUpScreen = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }
    )

    SignIn(
        showErrorMessage = { errorMessage ->
            showToast(context, errorMessage.toString())

        }
    )


}

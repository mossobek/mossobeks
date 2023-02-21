package com.stirkaparus.stirkaparus.presentation.login

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.stirkaparus.common.Utils.Companion.showMessage
import com.stirkaparus.stirkaparus.graphs.AuthScreen
import com.stirkaparus.stirkaparus.presentation.login.components.LoginContent
import com.stirkaparus.stirkaparus.presentation.login.components.LoginTopBar
import com.stirkaparus.stirkaparus.presentation.login.components.SignIn

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
                    viewModel.signInWithEmailAndPassword(email, password, context)
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
            showMessage(context, errorMessage)

        }
    )


}



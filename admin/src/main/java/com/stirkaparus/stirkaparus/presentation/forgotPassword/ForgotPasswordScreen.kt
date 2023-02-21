package com.stirkaparus.stirkaparus.presentation.forgotPassword

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.common.Constants.FORGOT_PASSWORD_SCREEN
import com.stirkaparus.stirkaparus.common.Constants.NO_VALUE
import com.stirkaparus.stirkaparus.common.Constants.RESET_PASSWORD
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.components.BackIcon
import com.stirkaparus.stirkaparus.presentation.components.EmailField
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            ForgotPasswordContent(
                padding = padding,
                sendPasswordResetEmail = { email ->
                    viewModel.sendPasswordResetEmail(email)
                }
            )
        }
    )
}

@Composable
fun ForgotPassword(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    showResetPasswordMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {

    when (val sendPasswordResetEmailResponse = viewModel.sendPasswordResetEmailResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> {
            val isPasswordResetEmailSent = sendPasswordResetEmailResponse.data
            LaunchedEffect(isPasswordResetEmailSent) {
                if (isPasswordResetEmailSent == true) {
                    navigateBack()
                    showResetPasswordMessage()
                }
            }
        }
        is Response.Failure -> sendPasswordResetEmailResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e?.message)
            }
        }

    }


}


@Composable
fun ForgotPasswordContent(
    padding: PaddingValues,
    sendPasswordResetEmail: (email: String) -> Unit,
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(TextFieldValue(NO_VALUE))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        SmallSpacer()
        Button(onClick = {
            sendPasswordResetEmail(email.text)
        }) {
            Text(
                text = RESET_PASSWORD,
                fontSize = 15.sp
            )
        }
    }

}

@Composable
fun ForgotPasswordTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = FORGOT_PASSWORD_SCREEN)
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )

}

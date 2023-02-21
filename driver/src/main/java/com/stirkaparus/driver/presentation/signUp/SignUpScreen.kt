package com.stirkaparus.driver.presentation.signUp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.driver.R
import com.stirkaparus.driver.common.Constants.ALREADY_USER
import com.stirkaparus.driver.common.Constants.NO_VALUE
import com.stirkaparus.driver.common.Constants.SIGN_UP
import com.stirkaparus.driver.common.Constants.SIGN_UP_SCREEN
import com.stirkaparus.driver.common.Constants.TAG
import com.stirkaparus.driver.common.Constants.VERIFY_EMAIL_MESSAGE
import com.stirkaparus.driver.common.showMessage
import com.stirkaparus.driver.common.showToast
import com.stirkaparus.driver.presentation.components.*
import com.stirkaparus.model.Response
import es.dmoral.toasty.Toasty

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current



    Scaffold(
        topBar = {
            SignInTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->

            SignUpContent(
                padding = padding,
                signUp = { email, password ->
                    viewModel.signUpWithEmailAndPassword(
                        email.trim().toString(),
                        password.trim().toString()
                    )

                },
                navigateBack = navigateBack
            )
        }
    )

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showMessage(context, VERIFY_EMAIL_MESSAGE)
        },
        showErrorMessage = { errorMessage ->
            Toasty.error(context, errorMessage.toString(),Toast.LENGTH_SHORT, true).show()
            //showToast(context, errorMessage.toString())

        }
    )

    SendEmailVerification()
}

@Composable
fun SendEmailVerification(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    when (val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpContent(
    padding: PaddingValues,
    signUp: (email: String, password: String) -> Unit,
    navigateBack: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(TextFieldValue(NO_VALUE))
    }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(TextFieldValue(NO_VALUE))
    }

    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painterResource(id = R.drawable.logodriver),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.height(30.dp))


        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        SmallSpacer()
        PasswordField(password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            })
        SmallSpacer()
        Button(onClick = {
            keyboard?.hide()
            signUp(email.text, password.text)

        }
        ) {
            Text(
                text = SIGN_UP,
                fontSize = 15.sp
            )
        }
        Text(
            modifier = Modifier.clickable {
                navigateBack()
            }, text = ALREADY_USER,
            fontSize = 15.sp
        )
    }
}

@Composable
fun SignUp(
    viewModel: SignUpViewModel = hiltViewModel(),
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit

) {
    val context = LocalContext.current
    when (val signUpResponse = viewModel.signUpResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp == true) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
//                showToast(context, e?.localizedMessage.toString())
                print(e)
                showErrorMessage(e?.message)
                Log.e(TAG, "SignUp: ERROR: $e")
            }
        }
    }

}

@Composable
fun SignInTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = SIGN_UP_SCREEN)
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )

        }

    )


}

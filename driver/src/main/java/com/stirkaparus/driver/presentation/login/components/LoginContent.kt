package com.stirkaparus.driver.presentation.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.driver.common.Constants.FORGOT_PASSWORD
import com.stirkaparus.driver.common.Constants.NO_ACCOUNT
import com.stirkaparus.driver.common.Constants.NO_VALUE
import com.stirkaparus.driver.common.Constants.SIGN_IN
import com.stirkaparus.driver.common.Constants.VERTICAL_DIVIDER
import com.stirkaparus.driver.presentation.components.EmailField
import com.stirkaparus.driver.presentation.components.PasswordField
import com.stirkaparus.driver.presentation.components.SmallSpacer


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {


    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }
    val keyboard = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(email = email, onEmailValueChange = { newValue ->
            email = newValue
        })
        SmallSpacer()
        PasswordField(password = password, onPasswordValueChange = { newValue ->
            password = newValue
        })
        SmallSpacer()
        Button(onClick = {
            keyboard?.hide()
            signIn(email.text, password.text)
        }) {
            Text(
                text = SIGN_IN, fontSize = 15.sp
            )
        }
        Row {
            Text(
                modifier = Modifier.clickable {
                    navigateToForgotPasswordScreen()
                }, text = FORGOT_PASSWORD, fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = VERTICAL_DIVIDER,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                }, text = NO_ACCOUNT, fontSize = 15.sp

            )

        }
    }



}



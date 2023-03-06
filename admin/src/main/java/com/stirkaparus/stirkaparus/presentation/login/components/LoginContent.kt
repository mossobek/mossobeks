package com.stirkaparus.stirkaparus.presentation.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.stirkaparus.R
import com.stirkaparus.stirkaparus.common.Constants.FORGOT_PASSWORD
import com.stirkaparus.stirkaparus.common.Constants.NO_ACCOUNT

import com.stirkaparus.stirkaparus.common.Constants.NO_VALUE
import com.stirkaparus.stirkaparus.common.Constants.SIGN_IN
import com.stirkaparus.stirkaparus.common.Constants.VERTICAL_DIVIDER
import com.stirkaparus.stirkaparus.presentation.components.EmailField
import com.stirkaparus.stirkaparus.presentation.components.PasswordField
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer


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
            .padding(padding)
            .padding(top = 80.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


            Image(

                painterResource(R.drawable.logoadmin),
                contentDescription = "",
                modifier = Modifier.size(148.dp)
            )


        SmallSpacer(36.dp)


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



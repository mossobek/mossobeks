package com.stirkaparus.driver.presentation.splash

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.R
import com.stirkaparus.driver.common.Constants.DRIVER
import com.stirkaparus.driver.presentation.TAG
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.driver.presentation.splash.components.CheckUserInFireStore
import com.stirkaparus.driver.presentation.user_details.UserDetailsViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import es.dmoral.toasty.Toasty

@Composable
fun SplashScreen(
    navToUserActivationScreen: () -> Unit,
    navToHomeScreen: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(145.dp),
            painter = painterResource(R.drawable.logodriver),
            contentDescription = null
        )
    }
    val context = LocalContext.current

    CheckUserInFireStore(
        userNotActive = {
            viewModel.signOut()
            Toasty.error(context,"Ваш профиль отключен",Toast.LENGTH_SHORT,true).show()
        }
    )


    if (!viewModel.getUserDataFromDataStore()) {

        viewModel.tryGetUserDataFromFireStore()

        when (val userDetailsResponse = viewModel.userDetailsResponse) {
            is Response.Loading -> ProgressDialog()
            is Response.Success -> {
                var user = User()
                userDetailsResponse.data?.let { userEx ->
                    user = userEx
                    Log.e(TAG, "SplashScreen userEx: $userEx")
                }
                Log.e(TAG, "VControl1: $user")

                if (user != null) {
                    if (
                        !user.id.isNullOrBlank() &&
                        !user.name.isNullOrBlank() &&
                        !user.city.isNullOrBlank() &&
                        !user.company_id.isNullOrBlank() &&
                        !user.company_name.isNullOrBlank() &&
                        !user.phone.isNullOrBlank() &&
                        !user.email.isNullOrBlank() &&
                        user.role == DRIVER
                    ) {
                        viewModel.setDataInSP(user)
                        LaunchedEffect(Unit) {
                            navToHomeScreen()
                        }
                    } else {
                        LaunchedEffect(Unit) {
                            navToUserActivationScreen()
                        }
                    }

                } else {
                    navToUserActivationScreen()
                }
            }
            is Response.Failure -> userDetailsResponse.apply {
                LaunchedEffect(e) {
                    print(e)
                    Toasty.error(context, e?.message.toString(), Toast.LENGTH_SHORT, true).show()
                    navToUserActivationScreen()
                }
            }
        }
    } else {
        LaunchedEffect(Unit) {
            navToHomeScreen()
        }
    }
}



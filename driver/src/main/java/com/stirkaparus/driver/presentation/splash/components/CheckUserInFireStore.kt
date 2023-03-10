package com.stirkaparus.driver.presentation.splash.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.user_details.UserDetailsViewModel
import com.stirkaparus.model.Response

@Composable
fun CheckUserInFireStore(
    userNotActive: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {


    viewModel.checkUser()
    when (val checkUserResponse = viewModel.checkUserResponse) {

        is Response.Loading -> Unit
        is Response.Success -> checkUserResponse.data.let {
            if (it == null || it.active != true) {

            userNotActive()
            }
        }
        is Response.Failure -> Unit

    }

}
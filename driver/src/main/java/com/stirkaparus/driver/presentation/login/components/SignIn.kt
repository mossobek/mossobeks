package com.stirkaparus.driver.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.driver.presentation.login.LoginViewModel
import com.stirkaparus.model.Response

@Composable
fun SignIn(
    viewModel: LoginViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when (val signInResponse = viewModel.signInResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e?.message)
            }
        }
    }
}

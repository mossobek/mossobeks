package com.stirkaparus.stirkaparus.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.login.LoginViewModel


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

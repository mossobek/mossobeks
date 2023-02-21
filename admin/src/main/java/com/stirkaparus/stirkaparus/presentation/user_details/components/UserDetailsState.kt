package com.stirkaparus.stirkaparus.presentation.user_details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.user_details.UserDetailsViewModel


const val TAG = "UserDetailsState"

@Composable
fun UserDetailsState(
    viewModel: UserDetailsViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {
     when (val signInResponse = viewModel.userAndCompanyDetailsResponse) {
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


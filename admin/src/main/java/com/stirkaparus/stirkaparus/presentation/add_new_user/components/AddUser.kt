package com.stirkaparus.stirkaparus.presentation.add_new_user.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.add_new_user.AddNewUsrViewModel
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog

@Composable
fun AddUser(
    navBack:()->Unit,
    viewModel: AddNewUsrViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {


    when (val addUserResponse = viewModel.addUserResponse) {

        is Response.Loading -> ProgressDialog()
        is Response.Success -> {
            val isUserAdded = addUserResponse.data
            LaunchedEffect(isUserAdded) {
                if (isUserAdded == true) {
                    navBack()
                }
            }


        }
        is Response.Failure -> addUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e?.message)
            }
        }
    }
}

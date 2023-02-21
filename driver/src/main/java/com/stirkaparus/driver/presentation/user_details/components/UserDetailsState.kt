package com.stirkaparus.driver.presentation.user_details.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.user_details.UserDetailsViewModel


const val TAG = "UserDetailsState"

@Composable
fun UserDetailsState(
    viewModel: UserDetailsViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {



}


package com.stirkaparus.stirkaparus.presentation.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel

////fun ProductsOrder(
//    viewModel: ProfileViewModel = hiltViewModel(),
//    usersListContent: @Composable (user: User) -> Unit
//) {
//    when (val usersListResponse = viewModel.usersListResponse) {
//        is Response.Loading -> ProgressDialog()
//        is Response.Success -> usersListResponse.data?.let { user ->
//            usersListContent(user)
//        }
//        is Response.Failure -> LaunchedEffect(Unit) {
//            print(usersListResponse.e)
//        }
//    }
//}
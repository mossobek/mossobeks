package com.stirkaparus.stirkaparus.presentation.user_details

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.stirkaparus.common.Utils.Companion.showMessage
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsContent
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsState
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserDetailsScreen(
    navController: NavHostController,
    navigateToMainScreen: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    Scaffold(topBar = {
        UserDetailsTopBar(signOut = {

            viewModel.signOut()
        })
    }, content = { padding ->
        UserDetailsContent(
            padding = padding
        )
    })


}




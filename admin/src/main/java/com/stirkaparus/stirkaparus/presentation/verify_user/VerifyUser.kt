package com.stirkaparus.stirkaparus.presentation.verify_user

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.stirkaparus.common.Utils.Companion.showMessage
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel
import com.stirkaparus.stirkaparus.presentation.user_details.UserDetailsViewModel
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsContent
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsState
import com.stirkaparus.stirkaparus.presentation.user_details.components.UserDetailsTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
 @Composable
fun UserDetailsScreen(
    navController: NavHostController,
    navigateToMainScreen: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        UserDetailsTopBar(signOut =  {

            viewModel.signOut()
        })
    }, content = { padding ->
        UserDetailsContent(
            padding = padding
        )
    })


}

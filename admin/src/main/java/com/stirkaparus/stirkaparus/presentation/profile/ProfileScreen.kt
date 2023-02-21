package com.stirkaparus.stirkaparus.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.profile.components.ProfileContent
import com.stirkaparus.stirkaparus.presentation.profile.components.ProfileTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    paddingBottom: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel(),
    navToAddUserScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            ProfileTopBar(navToAddUserScreen = navToAddUserScreen)
        },
        content = { padding ->
            ProfileContent(
                paddingBottom = paddingBottom,
                padding = padding
            )

        }
    )
}
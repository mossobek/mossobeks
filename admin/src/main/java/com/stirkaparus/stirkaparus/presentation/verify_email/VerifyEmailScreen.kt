package com.stirkaparus.stirkaparus.presentation.verify_email

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.common.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import com.stirkaparus.stirkaparus.common.Constants.VERIFY_EMAIL_SCREEN
import com.stirkaparus.stirkaparus.common.Utils.Companion.showMessage
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel
import com.stirkaparus.stirkaparus.presentation.profile.RevokeAccess
import com.stirkaparus.stirkaparus.presentation.verify_email.components.ReloadUser
import com.stirkaparus.stirkaparus.presentation.verify_email.components.VerifyEmailContent
import com.stirkaparus.stirkaparus.presentation.verify_email.components.VerifyTopBar

@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            VerifyTopBar(
                title = VERIFY_EMAIL_SCREEN,
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUser()
                }
            )
        },
        scaffoldState = scaffoldState
    )

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                showMessage(context, EMAIL_NOT_VERIFIED_MESSAGE)
            }
        }
    )

    RevokeAccess(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}
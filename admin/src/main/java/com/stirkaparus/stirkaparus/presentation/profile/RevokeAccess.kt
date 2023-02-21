package com.stirkaparus.stirkaparus.presentation.profile

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.common.Constants.ACCESS_REVOKED_MESSAGE
import com.stirkaparus.stirkaparus.common.Constants.REVOKE_ACCESS_MESSAGE
import com.stirkaparus.stirkaparus.common.Constants.SENSITIVE_OPERATION_MESSAGE
import com.stirkaparus.stirkaparus.common.Constants.SIGN_OUT
import com.stirkaparus.stirkaparus.common.Utils.Companion.showMessage
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked == true) {
                    showMessage(context, ACCESS_REVOKED_MESSAGE)
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e?.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}
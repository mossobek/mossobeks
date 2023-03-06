package com.stirkaparus.stirkaparus.presentation.reports

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsUserListContent
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsTopBar
import es.dmoral.toasty.Toasty

const val TAG = "ReportsScreen"

@SuppressLint("UnrememberedMutableState")
@Composable
fun ReportsScreen(
    navigateToUserOrdersScreen: (userId: String,username:String) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    navController: NavController,
    bottomPadding: PaddingValues,
) {


    Scaffold(
        topBar = {
            ReportsTopBar()
        },
        content = { padding ->
            viewModel.getUserList()
            when (val reportOrderListResponse = viewModel.reportOrderListResponse) {
                is Response.Loading -> ProgressDialog()
                is Response.Success -> {
                    ReportsUserListContent(
                        padding = padding,
                        userList = reportOrderListResponse.data!!,
                        navigateToUserOrdersScreen = {userId,username->
                            navigateToUserOrdersScreen(userId,username)
                        }
                    )
                }
                is Response.Failure -> Toasty.error(
                    LocalContext.current,
                    reportOrderListResponse.e?.message.toString(),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }

        }
    )
}


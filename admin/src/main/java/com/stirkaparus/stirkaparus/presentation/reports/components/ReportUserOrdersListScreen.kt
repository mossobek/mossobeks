package com.stirkaparus.stirkaparus.presentation.reports.components

import android.util.Log
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel
import es.dmoral.toasty.Toasty

@Composable
fun ReportUserOrdersListScreen(
    username: String,
    viewModel: ReportsViewModel = hiltViewModel(),
    id: String,
    navBack: () -> Unit,
    navigateToOrderScreen: (orderId: String) -> Unit,
) {
    Log.e(TAG, "ReportUserOrdersListScreen: $username")
    Scaffold(topBar = {
        ReportUserOrdersTopBar(navBack = navBack, username = username)
    }, content = { padding ->
        viewModel.reportUserOrdersList(id)
        when (val reportUserOrdersListResponse = viewModel.reportUserOrdersListResponse) {
            is Response.Loading -> ProgressDialog()
            is Response.Success -> {
                ReportUserOrdersListContent(id = id,
                    padding = padding,
                    userOrderList = reportUserOrdersListResponse.data!!,
                    navigateToOrderScreen = { orderId ->
                        navigateToOrderScreen(orderId)
                    })
            }
            is Response.Failure -> Toasty.error(
                LocalContext.current,
                reportUserOrdersListResponse.e?.message.toString(),
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    })
}


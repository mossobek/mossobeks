package com.stirkaparus.stirkaparus.presentation.reports

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsOrderListContent
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsTopBar
import es.dmoral.toasty.Toasty

const val TAG = "ReportsScreen"

@SuppressLint("UnrememberedMutableState")
@Composable
fun ReportsScreen(
    navigateToOrderDetailsScreen: (order: Order) -> Unit,
    onSearchIconClick: () -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    navController: NavController,
    bottomPadding: PaddingValues,
) {
    var allOrderList: MutableList<Order>

    Scaffold(
        topBar = {
            ReportsTopBar()
        },
        content = { padding ->
            viewModel.getOrderList()
            when (val reportOrderListResponse = viewModel.reportOrderListResponse) {
                is Response.Loading -> ProgressDialog()
                is Response.Success -> {


                    ReportsOrderListContent(
                        padding = padding,
                        orderList = reportOrderListResponse.data!! ,
                        navigateToOrderDetailsScreen = navigateToOrderDetailsScreen
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


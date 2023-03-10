package com.stirkaparus.driver.presentation.report.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.driver.presentation.report.ReportsViewModel
import com.stirkaparus.driver.ui.theme.BackgroundColor
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response


@Composable
fun ReportsContent(
    viewModel: ReportsViewModel = hiltViewModel(),
    errorMessage: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Divider(thickness = 1.dp)
        when (val reportListResponse = viewModel.reportListResponse) {
            is Response.Loading -> ProgressDialog()
            is Response.Success -> reportListResponse.data?.let { orders ->
                ReportOrdersList(orders)
            }
            is Response.Failure -> {
                errorMessage(reportListResponse.e?.message.toString())
            }
        }


    }
}


@Composable
fun ReportOrdersList(orders: List<Order>) {

}
package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.domain.repository.ReportsItems
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.components.ProgressBar
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel

@Composable
fun ReportsOrder(
    viewModel: ReportsViewModel = hiltViewModel(),
    reportsOrdersItemsContent: @Composable (orders: ReportsItems) -> Unit
) {
    when (val reportsOrdersResponse = viewModel.reportsOrdersResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> reportsOrdersResponse.data?.let { orders ->
            reportsOrdersItemsContent(orders)
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(reportsOrdersResponse.e)
        }
    }
}
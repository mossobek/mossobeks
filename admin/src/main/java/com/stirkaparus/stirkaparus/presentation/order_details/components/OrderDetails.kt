package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel

@Composable
fun OrderDetails(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    orderDetailsContent: @Composable (order: Order) -> Unit,
    errorMessage: (e: String) -> Unit,
) {
    when (val orderDetailsResponse = viewModel.orderDetailsResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> orderDetailsResponse.data?.let { order ->
            orderDetailsContent(order)
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            errorMessage(orderDetailsResponse.e?.message.toString())
        }
    }
}
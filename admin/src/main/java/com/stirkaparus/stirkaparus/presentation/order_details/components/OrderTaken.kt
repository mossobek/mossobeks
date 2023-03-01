package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel

@Composable
fun OrderTaken(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {

    when (val orderTakenResponse = viewModel.orderTakenResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> LaunchedEffect(Unit) {
            print(orderTakenResponse.e)
        }
    }

}
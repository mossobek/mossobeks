package com.stirkaparus.driver.presentation.orders.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.domain.repository.Orders
import com.stirkaparus.driver.presentation.orders.OrdersViewModel
import com.stirkaparus.model.Response

@Composable
fun Orders(
    viewModel: OrdersViewModel = hiltViewModel(),
    ordersContent: @Composable (orders: Orders) -> Unit
) {
    when (val ordersResponse = viewModel.ordersResponse) {
        is Response.Loading -> {

        }
        is Response.Success -> {
            Log.e(TAG, "Orders: ${ordersResponse.data}")
            ordersResponse.data?.let { ordersContent(it) }
        }
        is Response.Failure -> {
            Log.e(TAG, "FinishedOrders: ${ordersResponse.e}")
        }
    }
}
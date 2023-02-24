package com.stirkaparus.stirkaparus.presentation.reports.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel

const val TAG = "ReportsOrderListContent"

@Composable
fun ReportsOrderListContent(
    padding: PaddingValues,
    orderList: List<Order>,
    navigateToOrderDetailsScreen: (order: Order) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),

    ) {
    val selectedUserId = remember { mutableStateOf("") }
    val drivers by viewModel.drivers.observeAsState(initial = emptyList())
    Column(Modifier.fillMaxSize()) {
        LaunchedEffect(Unit) {
            viewModel.getMutableDriversList()
        }

        SpecimenSpinner(
            drivers = drivers
        )

        Log.e(TAG, "ReportsOrderListContent: $orderList", )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items = orderList
            ) { order ->
                ReportItemCard(
                    order = order,
                    onClick = {
                        navigateToOrderDetailsScreen(order)
                    }
                )
            }
        }
    }
}
package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel


@Composable
fun ReportUserOrdersListContent(
    padding: PaddingValues,
    id: String,
    viewModel: ReportsViewModel = hiltViewModel(),
    userOrderList: List<Order>,
    navigateToOrderScreen: (orderId: String) -> Unit
) {


    var total = 0
    var count by remember {
        mutableStateOf(0)
    }
    count = userOrderList.size
    for (i in userOrderList) {
        total += i.total!!
    }

    ReportUserDialog(
        count = count,
        total = total,
        openDialog = viewModel.userReportDialog,
        closeDialog = {
            viewModel.closeUserReportDialog()
        },
        report = {
            viewModel.doReport(id)
        }
    )


    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(Modifier.weight(1f)) {
                Text(fontSize = 18.sp, text = "Сумма :${total.toString()}")
            }
            Row(Modifier.weight(1f)) {
                Text(fontSize = 18.sp, text = "Заказов :$count")

            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items = userOrderList
            ) { order ->
                ReportUserOrderItemCard(order = order, onClick = {
                    navigateToOrderScreen(order.id.toString())
                })
            }
        }
    }
}

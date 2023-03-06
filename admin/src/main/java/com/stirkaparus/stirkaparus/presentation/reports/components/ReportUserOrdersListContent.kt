package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants.NO_VALUE
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel
import kotlinx.coroutines.job


@Composable
fun ReportUserOrdersListContent(
    padding: PaddingValues,
    id: String,
    viewModel: ReportsViewModel = hiltViewModel(),
    userOrderList: List<Order>,
    navigateToOrderScreen: (orderId: String) -> Unit
) {
    ReportUserDialog(
        openDialog = viewModel.userReportDialog,
        closeDialog = {
            viewModel.closeUserReportDialog()
        },
        report = {
            viewModel.doReport(id)
        }
    )

    var total = 0
    var count by remember {
        mutableStateOf("")
    }
    count = userOrderList.size.toString()
    for (i in userOrderList) {
        total += i.total!!
    }



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

@Composable
fun ReportUserDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    report: () -> Unit,

    ) {
    if (openDialog) {


        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(
                    text = "Сделать рассчёт"
                )
            },
            text = {
                Column {

                    SmallSpacer()
                    Text(text = "Сделать рассчет?")
                    SmallSpacer()

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        report()
                        closeDialog()

                    }
                ) {
                    Text(
                        text = "Рассчет"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(
                        text = "Зкрыть"
                    )
                }
            }
        )
    }
}

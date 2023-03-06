package com.stirkaparus.stirkaparus.presentation.orders_list_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportUserOrderItemCard
import com.stirkaparus.stirkaparus.ui.theme.BackgroundColor

@Composable
fun OrdersContent(
    paddingValue:PaddingValues,
    padding: PaddingValues,
    orders: List<Order>,
    navToOrder: (userId:String) -> Unit
) {


    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxWidth()
            .padding(top = 6.dp)
            .padding(bottom = paddingValue.calculateBottomPadding() )
            .padding(start = 6.dp)
            .padding(end = 6.dp)
    ) {

        Spacer(modifier = Modifier.height(6.dp))



        if (orders.isEmpty()) {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(start = 6.dp)
                    .padding(end = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(fontSize = 25.sp, text = "Список пуст")
            }
        }



        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(orders) { order ->
                OrderItem(
                    onClick = {
                            navToOrder(it)
                    },
                    order = order
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }

}

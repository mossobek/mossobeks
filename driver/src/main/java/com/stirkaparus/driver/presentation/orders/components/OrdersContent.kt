package com.stirkaparus.driver.presentation.orders.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
 import com.stirkaparus.driver.domain.repository.Orders
import com.stirkaparus.driver.graphs.Graph
import com.stirkaparus.model.Order


@SuppressLint("UnrememberedMutableState")
@Composable
fun OrdersContent(
    status:String = "created",
    searchText: MutableState<String>,
    padding: PaddingValues,
    orders: Orders,
    navController: NavController
) {

    val tempList = mutableStateListOf<Order>()

    SearchListReturn(searchText, orders, tempList)

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {

        items(
            items = tempList
        ) { order ->

            CreatedOrderItem(
                status = status,
                order = order,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        navController.navigate(Graph.ORDER_DETAIL + "/${order.id}")
                        Log.e(TAG, "CREATED_ORDER_DETAIL: ${order.id}")
                    }),

                onPhoneClick = {
                    val callPhone = order.phone
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:$callPhone")
                    ContextCompat.startActivity(context, callIntent, null)
                }
            )

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun SearchListReturn(
    searchText: MutableState<String>,
    orders: Orders,
    tempList: SnapshotStateList<Order>
)  {
    if (searchText.value != "") {
        orders.forEach {
            if (it.phone.toString().contains(searchText.value)) {
                Log.e(TAG, "OrdersContent: $it")
                tempList.add(it)
            }
        }
    } else {
        orders.forEach {
            tempList.add(it)
        }
    }
}
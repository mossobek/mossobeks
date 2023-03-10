package com.stirkaparus.driver.presentation.all_orders

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.driver.presentation.all_orders.components.AllOrdersTopBar
import com.stirkaparus.driver.presentation.all_orders.components.OrderItem
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.driver.ui.theme.BackgroundColor
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import es.dmoral.toasty.Toasty

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllOrders(
    navigateToOrderScreen: (orderId: String) -> Unit,
    navController: NavController,
    paddingValue: PaddingValues,
    viewModel: AllOrdersViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AllOrdersTopBar()
        },
        content = { padding ->
            when (val orderListResponse = viewModel.orderListResponse) {
                is Response.Loading -> ProgressDialog()
                is Response.Success -> {
                    AllOrdersContent(
                        padding = padding,
                        paddingValue = paddingValue,
                        orders = orderListResponse.data!!,
                        navToOrder = { orderId ->
                            navigateToOrderScreen(orderId)
                        }
                    )
                }
                is Response.Failure -> Toasty.error(
                    LocalContext.current,
                    orderListResponse.e?.message.toString(),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }


        }
    )
}


@Composable
fun AllOrdersContent(
    navToOrder: (orderId: String) -> Unit,
    orders: List<Order>,
    padding: PaddingValues,
    paddingValue: PaddingValues
) {
    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxWidth()
            .padding(top = 6.dp)
            .padding(bottom = paddingValue.calculateBottomPadding())
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
                    onClick = { orderId ->
                        navToOrder(orderId)
                    },
                    order = order
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

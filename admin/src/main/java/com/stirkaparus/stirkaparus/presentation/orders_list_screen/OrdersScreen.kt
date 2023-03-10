package com.stirkaparus.stirkaparus.presentation.orders_list_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.OrdersContent
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.OrdersTopBar
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun OrdersScreen(
    navController: NavHostController,
    paddingValue: PaddingValues,
    viewModel: OrdersViewModel = hiltViewModel(),
    navigateToUserOrdersScreen: (orderId: String) -> Unit
) {
    val scope = rememberCoroutineScope()

    val sortBottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sortBottomState,
        sheetContent = {
            Button(onClick = {
                scope.launch {
                    sortBottomState.hide()
                }

            }) {

            }
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 12.dp,
    ) {

        Scaffold(
            topBar = {
                OrdersTopBar()
            },
            content = { padding ->
                viewModel.getOrders()
                when (val orderListResponse = viewModel.orderListResponse) {
                    is Response.Loading -> ProgressDialog()
                    is Response.Success -> {
                        OrdersContent(
                            padding = padding,
                            paddingValue = paddingValue,
                            orders = orderListResponse.data!!,
                            navToOrder = { userId ->
                                navigateToUserOrdersScreen(userId)
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


}


sealed class OrderDetailsScreen(val route: String) {

    object OrderDetails : OrderDetailsScreen(route = "ORDER_DETAIL")
    object Edit : OrderDetailsScreen(route = "EDIT1")
    object Carpets : OrderDetailsScreen(route = "CARPETS")
}


@Composable
private fun filterOrder(
    selectedFilter: MutableState<String>,
    posts: List<Order?>
): List<Order?> {
    val filteredPosts = when (selectedFilter.value) {
        Filter.created -> {
            posts.filter { it?.status == "created" }
        }
        Filter.taken -> {
            posts.filter { it?.status == "taken" }
        }
        Filter.washed -> {
            posts.filter { it?.status == "washed" }
        }
        Filter.finished -> {
            posts.filter { it?.status == "finished" }
        }
        Filter.delivered -> {
            posts.filter { it?.status == "delivered" }
        }
        Filter.all -> {
            posts
        }
        else -> {
            posts.filter { it?.status != "deleted" }
        }
    }
    return filteredPosts
}

object Filter {
    const val all = "all"
    const val created = "created"
    const val taken = "taken"
    const val washed = "washed"
    const val finished = "finished"
    const val delivered = "delivered"
}







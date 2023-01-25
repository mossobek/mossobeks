package com.stirkaparus.stirkaparus.screens.order_details_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.ProgressDialog
import com.stirkaparus.stirkaparus.screens.order_details_screen.components.CustomTextFieldRow
import com.stirkaparus.stirkaparus.screens.order_details_screen.components.OrderDetailsStatusComponent
import com.stirkaparus.stirkaparus.screens.order_details_screen.components.StatusChange
import com.stirkaparus.stirkaparus.screens.order_edit_screen.showToast
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrderDetailsScreen1
import com.stirkaparus.stirkaparus.screens.orders_list_screen.components.formatDate
import com.stirkaparus.stirkaparus.screens.orders_list_screen.components.setStatus

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(navController: NavController) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    val viewModel: OrderDetailsScreenViewModel = OrderDetailsScreenViewModel()
    val orderResource by viewModel.getOrder(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val order = orderResource.data ?: Order()
    val context = LocalContext.current

    var loading by remember { mutableStateOf(false) }
    var alertDialogState by remember { mutableStateOf(false) }

    if (alertDialogState) StatusChange(
        order.status,
        onClick = { status->

            viewModel.changeStatus(
                id,
                status.status,
                success = {
                          alertDialogState = false
                },
                failure = {
                    alertDialogState = false

                }
            )
        },
        onDismiss = {alertDialogState = false}
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            loading = ProgressDialog(loading)
            OrderDetailsTopBarComponent(
                navController, id.toString(),
                deleteOrder = {
                    loading = true
                    viewModel.deleteOrder(
                        id,
                        success = {
                            navController.popBackStack()
                            showToast(context, "Заказ удален!")
                            loading = false
                        },
                        failure = {
                            showToast(context, "Что то пошло не так...")
                            loading = false

                        })
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            CustomTextFieldRow(
                description = "Телефон",
                text = order.phone.toString(),
                icon = Icons.Default.Phone
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Адрес",
                text = order.address.toString(),
                icon = Icons.Default.LocationOn
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Комментарий",
                text = order.comment.toString(),
                icon = Icons.Default.Comment
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsStatusComponent(
                status = setStatus(order.status.toString(), order),
                description = "Статус",
                text = order.status.toString(),
                icon = Icons.Default.CheckCircle,
                onClock = { alertDialogState = true}
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Количество",
                text = order.count.toString(),
                icon = Icons.Default.AutoAwesomeMotion,
                secondIcon = Icons.Default.AddBox,
                onClick = {
                    navController.navigate(OrderDetailsScreen1.Carpets.route + "/${id}")
                },
                clickable = true
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Сумма",
                text = order.total.toString(),
                icon = Icons.Default.Money
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFieldRow(
                description = "Создан",
                text = formatDate(order.create_time),
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Забран",
                text = formatDate(order.taken_time),
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Постиран",
                text = formatDate(order.washed_time),
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Доставлен",
                text = formatDate(order.delivered_time),
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
    //SetDetails(order = orderDetails)

}



@Composable
fun OrderDetailsTopBarComponent(navController: NavController, id: String, deleteOrder: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier

        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close icon button"
                )
            }
        }
        Row(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier, style = MaterialTheme.typography.h6, text = "Детали заказа"
            )
        }
        Row(
            modifier = Modifier
        ) {
            IconButton(
                modifier = Modifier
                    .padding(end = 0.dp),
                onClick = {
                    navController.navigate(OrderDetailsScreen1.Edit1.route + "/${id}")
                }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit icon button"
                )
            }
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {
                deleteOrder()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon button"
                )
            }
        }
    }
}





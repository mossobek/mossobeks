package com.stirkaparus.stirkaparus.presentation.order_details_screen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.formatDate
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetAlertDialog
import com.stirkaparus.stirkaparus.presentation.order_details_screen.components.CustomTextFieldRow
import com.stirkaparus.stirkaparus.presentation.order_details_screen.components.OrderDetailsStatusComponent
import com.stirkaparus.stirkaparus.presentation.order_details_screen.components.OrderDetailsTopBarComponent
import com.stirkaparus.stirkaparus.presentation.order_details_screen.components.StatusChange
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.showToast
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrderDetailsScreen1
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.setStatus

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(navController: NavController) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    val viewModel = OrderDetailsScreenViewModel()
    val orderResource by viewModel.getOrder(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val order = orderResource.data ?: Order()
    val context = LocalContext.current
    var visibleAddButton by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var alertDialogState by remember { mutableStateOf(false) }
    var carpetAlertDialogState by remember { mutableStateOf(false) }
    val viewModelCarpets = CarpetsViewModel()
    val status by viewModel.orderStatus.observeAsState()

    Log.e(TAG, "OrderDetailsScreen: status$id")
    Log.e(TAG, "OrderDetailsScreen: order$order")

    visibleAddButton = if (status == Constants.TAKEN) {
        Log.e(TAG, "OrderDetailsScreen: visibleAddButton = true ")
        true
    } else {
        Log.e(TAG, "OrderDetailsScreen: visibleAddButton = false ")
        false
    }

    if (alertDialogState) StatusChange(order.status, onClick = { status ->

        viewModel.changeStatus(id, status.status, success = {
            alertDialogState = false
        }, failure = {
            alertDialogState = false

        })
    }, onDismiss = { alertDialogState = false })

    if (carpetAlertDialogState) AddCarpetAlertDialog(id = id.toString(),
        viewModel = viewModelCarpets,
        context = context,
        onClick = {},
        onDismiss = { carpetAlertDialogState = false })


    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (loading)  ProgressDialog()
            OrderDetailsTopBarComponent(navController, id.toString(), deleteOrder = {
                loading = true
                viewModel.deleteOrder(id, success = {
                    navController.popBackStack()
                    showToast(context, "Заказ удален!")
                    loading = false
                }, failure = {
                    showToast(context, "Что то пошло не так...")
                    loading = false

                })
            })

            Spacer(modifier = Modifier.height(16.dp))
            CustomTextFieldRow(
                description = "Телефон", text = order.phone.toString(), icon = Icons.Default.Phone
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
                onClock = {
                    alertDialogState = true
                })
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                visibleAddButton = visibleAddButton,
                description = "Количество",
                text = order.count.toString(),
                icon = Icons.Default.AutoAwesomeMotion,
                secondIcon = if (visibleAddButton) {
                    Icons.Default.AddBox
                } else {
                    null
                },
                onClick = {
                    navController.navigate(OrderDetailsScreen1.Carpets.route + "/${id}")
                },
                clickable = true,
                secondIconOnClick = {
                    carpetAlertDialogState = true
                })
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Из них постирано",
                text = order.washed_count.toString(),
                icon = Icons.Default.AutoAwesomeMotion,
                clickable = false
            )
            Spacer(modifier = Modifier.height(2.dp))

            CustomTextFieldRow(
                description = "Сумма", text = order.total.toString(), icon = Icons.Default.Money
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFieldRow(
                description = "Создан", text = formatDate(order.created_time), paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Забран", text = formatDate(order.taken_time), paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            CustomTextFieldRow(
                description = "Постиран", text = formatDate(order.washed_time), paddingStart = 16.dp
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

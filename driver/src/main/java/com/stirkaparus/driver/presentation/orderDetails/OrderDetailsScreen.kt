package com.stirkaparus.driver.presentation.orderDetails

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.presentation.createdOrderDetails.OrderDetailsScreenViewModel
import com.stirkaparus.driver.presentation.createdOrderDetails.Resource
import com.stirkaparus.driver.presentation.createdOrderDetails.components.CustomTextFieldRow
import com.stirkaparus.driver.presentation.createdOrderDetails.components.DeliveryOrderDialog
import com.stirkaparus.driver.presentation.createdOrderDetails.components.TakeOrderDialog
import com.stirkaparus.driver.presentation.orderDetails.components.CancelOrderDialogAlert
import com.stirkaparus.driver.presentation.orderDetails.components.TopBar
import com.stirkaparus.driver.presentation.orders.components.ButtonsRow
import com.stirkaparus.driver.presentation.orders.components.formatDate
import com.stirkaparus.driver.ui.theme.BlueStatusColor
import com.stirkaparus.driver.ui.theme.GreenStatusColor
import com.stirkaparus.model.Order
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(
    navController: NavController,
) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    Log.e(ContentValues.TAG, "OrderDetailsScreen: $id")
    val viewModel = OrderDetailsScreenViewModel()
    val orderResource by viewModel.getOrder(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val order = orderResource.data ?: Order()
    val context = LocalContext.current
    var takeOrderDialogState by remember { mutableStateOf(false) }
    var deliveryOrderDialogState by remember { mutableStateOf(false) }
    val loadingCircle = remember { mutableStateOf(false) }
    val cancelAlert = remember { mutableStateOf(false) }
    var statusDetails: StatusDetails = StatusDetails.Created

    if (order.status == Constants.FINISHED) {
        statusDetails = StatusDetails.Finished
    }

    if (cancelAlert.value) {
        CancelOrderDialogAlert(onDismiss = {
            cancelAlert.value = false
        }, cancelOrder = {

            loadingCircle.value = true
            viewModel.cancelOrder(id.toString(), success = {
                loadingCircle.value = false
                cancelAlert.value = false

            }, failure = {
                loadingCircle.value = false

            })


        }, loadingCircle
        )
    }


    if (takeOrderDialogState) TakeOrderDialog(
        onDismiss = { takeOrderDialogState = false },
        addCount = { count ->
            loadingCircle.value = true
            viewModel.addCount(id.toString(), count, success = {
                loadingCircle.value = false
                takeOrderDialogState = false
                navController.popBackStack()
            }, failure = {
                loadingCircle.value = false

            })
        },
        loadingState = loadingCircle

    )
    if (deliveryOrderDialogState) DeliveryOrderDialog(
        onDismiss = { deliveryOrderDialogState = false },
        updateOrder = {
            loadingCircle.value = true
            viewModel.updateOrder(id.toString(),
                success = {
                    loadingCircle.value = false
                    deliveryOrderDialogState = false
                    navController.popBackStack()
                }, failure = {
                    loadingCircle.value = false

                })
        },
        loadingState = loadingCircle

    )
    Log.e(ContentValues.TAG, "OrderDetailsScreen: $id")

    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray,


        ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                //  Indicator()

                TopBar(
                    statusDetails = statusDetails,
                    navController = navController,
                    cancelAlert = cancelAlert,
                    text = "Детали Заказа"
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
                Spacer(modifier = Modifier.height(20.dp))


                when (statusDetails) {
                    is StatusDetails.Created -> {
                        CustomTextFieldRow(
                            description = "Создан",
                            text = formatDate(order.created_time),
                            paddingStart = 16.dp
                        )
                    }
                    is StatusDetails.Finished -> {
                        CustomTextFieldRow(
                            description = "Готов",
                            text = formatDate(order.finished_time),
                            paddingStart = 16.dp
                        )
                        CustomTextFieldRow(
                            description = "Количество",
                            text = order.count.toString(),
                            paddingStart = 16.dp
                        )
                        CustomTextFieldRow(
                            description = "Сумма",
                            text = order.total.toString(),
                            paddingStart = 16.dp
                        )
                    }
                }
            }


            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                ButtonsRow(
                    order,
                    context,
                    statusDetails,
                    takenClick = {
                        takeOrderDialogState = true
                    }, deliveredClick = {
                        deliveryOrderDialogState = true
                    })

            }
        }
    }
}

sealed class StatusDetails(val status: String, color: Color = Color.Blue) {
    object Created : StatusDetails(status = Constants.CREATED, color = BlueStatusColor)
    object Finished : StatusDetails(status = Constants.FINISHED, color = GreenStatusColor)
}
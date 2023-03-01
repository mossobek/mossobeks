package com.stirkaparus.stirkaparus.presentation.order_details.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetSheet
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable

fun OrderDetailsContent(
    navToCarpets: () -> Unit,
    id: String,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val statusBottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentSheetContent by remember {
        mutableStateOf<BottomSheetScreen>(BottomSheetScreen.Screen1)
    }
    val context = LocalContext.current
    var onOrderTakeClicked by remember {
        mutableStateOf(false)
    }

    var order by remember {
        mutableStateOf(Order())
    }
    LaunchedEffect(id) {
        viewModel.getOrder(id)
    }
    when (val orderDetailsResponse = viewModel.orderDetailsResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> orderDetailsResponse.data?.let { extOrder ->
            order = extOrder
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            Toasty.error(
                context,
                orderDetailsResponse.e?.message.toString(),
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    }



    OrderTaken()
    DeliveryTaken()
    Log.e(TAG, "OrderDetailsContent: ${viewModel.orderTakenResponse}")
    if (onOrderTakeClicked) {
        OrderTakeDialog(
            id = id,
            onDismiss = {
                onOrderTakeClicked = false
            },
            onConfirm = { count ->
                viewModel.orderTaken(
                    id = id, count = count.toInt()
                )
                onOrderTakeClicked = false
            }
        )
    }

    ModalBottomSheetLayout(
        sheetState = statusBottomState,
        sheetContent = {
            Log.e(TAG, "OrderDetailsContent: $currentSheetContent")
            when (currentSheetContent) {
                is BottomSheetScreen.Screen1 -> {
                    StatusChange(
                        id = id,
                        onOrderTakeClicked = {
                            onOrderTakeClicked = true
                            scope.launch {
                                statusBottomState.hide()
                            }
                        },
                    )
                }
                is BottomSheetScreen.Screen2 -> {
                    AddCarpetSheet(id)
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 12.dp
    ) {
        OrderTextFieldBox(
            order = order,
            onStatusClick = {
                scope.launch {
                    currentSheetContent = BottomSheetScreen.Screen1
                    statusBottomState.show()
                }
            },
            onAddCarpetClick = {
                scope.launch {
                    currentSheetContent = BottomSheetScreen.Screen2
                    statusBottomState.show()
                }
            },
            navToCarpets = {
                navToCarpets()
            })
    }

}

@Composable
fun DeliveryTaken(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {

    when (val orderDeliveredResponse = viewModel.orderDeliveredResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> LaunchedEffect(Unit) {
            print(orderDeliveredResponse.e)
        }
    }

}

sealed class BottomSheetScreen() {
    object Screen1 : BottomSheetScreen()
    object Screen2 : BottomSheetScreen()

}
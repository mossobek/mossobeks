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
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetSheet
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

lateinit var order: Order

@OptIn(ExperimentalMaterialApi::class)
@Composable

fun OrderDetailsContent(
    navToCarpets: () -> Unit, id: String, viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    var status by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    LaunchedEffect(id) {
        viewModel.getOrder(id)
    }

    val scope = rememberCoroutineScope()
    val statusBottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currentSheetContent by remember {
        mutableStateOf<BottomSheetScreen>(BottomSheetScreen.Screen1)
    }

    ModalBottomSheetLayout(
        sheetState = statusBottomState,
        sheetContent = {

            Log.e(TAG, "OrderDetailsContent: $currentSheetContent")
            when (currentSheetContent) {
                is BottomSheetScreen.Screen1 -> {
                    StatusChange(
                        currentSt = status,
                        onClick = { status ->
                            viewModel.changeStatus(
                                id = id,
                                status = status.status,
                                success = {
                                    scope.launch {
                                        statusBottomState.hide()
                                    }
                                },
                                failure = {

                                }
                            )
                        }
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


        OrderDetails(errorMessage = { errorMessage ->
            Toasty.error(context, errorMessage, Toast.LENGTH_SHORT, true).show()
        }, orderDetailsContent = { order ->
            status = order.status.toString()

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
        })
    }
}

sealed class BottomSheetScreen() {
    object Screen1 : BottomSheetScreen()
    object Screen2 : BottomSheetScreen()

}
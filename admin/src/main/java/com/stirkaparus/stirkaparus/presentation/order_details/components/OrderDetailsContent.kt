package com.stirkaparus.stirkaparus.presentation.order_details.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpet
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetSheet
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable

fun OrderDetailsContent(
    order: Order,
    navToCarpets: () -> Unit,
    id: String,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    carpetsViewModel: CarpetsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    if (carpetsViewModel.addCarpetResponse == Response.Loading) {
        ProgressDialog()

    }
    val scope = rememberCoroutineScope()
    val statusBottomState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    var currentSheetContent by remember {
        mutableStateOf<BottomSheetScreen>(BottomSheetScreen.StatusChangeScreen)
    }




    DeliveredStatus(
        id = id,
        onDismiss = {
            scope.launch {

                statusBottomState.hide()
            }
        })
    AddCarpet()
    OrderTaken()
    DeliveryOrder()
    OrderTakeDialog(
        openDialog = viewModel.takeDialog,
        id = id
    )

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxHeight(), sheetState = statusBottomState, sheetContent = {
            Log.e(TAG, "OrderDetailsContent: $currentSheetContent")
            when (currentSheetContent) {
                is BottomSheetScreen.StatusChangeScreen -> {

                    StatusChange(
                        order= order,
                        id = id,
                    )
                }
                is BottomSheetScreen.AddCarpetSheetScreen -> {
                    AddCarpetSheet(id = id) {
                        scope.launch {
                            statusBottomState.hide()
                        }
                    }
                }
            }
        }, sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp), sheetElevation = 12.dp
    ) {
        OrderTextFieldBox(order = order,
            onStatusClick = {

                if (order.reported == false){

                    scope.launch {
                        currentSheetContent = BottomSheetScreen.StatusChangeScreen
                        statusBottomState.animateTo(ModalBottomSheetValue.Expanded)
                    }


                } else{
                    Toasty.error(context,"Заказ нельзя изменить", Toast.LENGTH_SHORT,true).show()
                }



        }, onAddCarpetClick = {
            scope.launch {
                currentSheetContent = BottomSheetScreen.AddCarpetSheetScreen
                statusBottomState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }, navToCarpets = {
            navToCarpets()
        })
    }
}

sealed class BottomSheetScreen() {
    object StatusChangeScreen : BottomSheetScreen()
    object AddCarpetSheetScreen : BottomSheetScreen()
}
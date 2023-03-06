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
    if (carpetsViewModel.addCarpetResponse == Response.Loading) {
        ProgressDialog()

    }
    val scope = rememberCoroutineScope()
    val statusBottomState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    var currentSheetContent by remember {
        mutableStateOf<BottomSheetScreen>(BottomSheetScreen.Screen1)
    }





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
                is BottomSheetScreen.Screen1 -> {
                    StatusChange(
                        id = id,
                    )
                }
                is BottomSheetScreen.Screen2 -> {
                    AddCarpetSheet(id = id) {
                        scope.launch {
                            statusBottomState.hide()
                        }
                    }
                }
            }
        }, sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp), sheetElevation = 12.dp
    ) {
        OrderTextFieldBox(order = order, onStatusClick = {
            scope.launch {
                currentSheetContent = BottomSheetScreen.Screen1
                statusBottomState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }, onAddCarpetClick = {
            scope.launch {
                currentSheetContent = BottomSheetScreen.Screen2
                statusBottomState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }, navToCarpets = {
            navToCarpets()
        })
    }
}

sealed class BottomSheetScreen() {
    object Screen1 : BottomSheetScreen()
    object Screen2 : BottomSheetScreen()
}
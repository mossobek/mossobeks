package com.stirkaparus.stirkaparus.presentation.order_details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.order_details.components.*
import es.dmoral.toasty.Toasty

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(
     id: String,
    navBack: () -> Unit,
    navToCarpets: () -> Unit,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var order by remember {
        mutableStateOf(Order())
    }
    LaunchedEffect(id) {
        viewModel.getOrder(id)
    }
    GetOrder(
        orderResponse = {
            order = it
        },
        error = { errorMessage ->
            Toasty.error(
                context, errorMessage, Toast.LENGTH_SHORT, true
            ).show()
        }
    )
    EditOrderDialog(
        order = order,
        openDialog = viewModel.editOrderDialog
    )
    DeleteOrderDialog(
        navBack = navBack,
        id = order.id.toString(),
        openDialog = viewModel.deleteOrderDialog
    )
    Scaffold(
        topBar = {
            OrderDetailsTopBar(
                editOrder = {
                    viewModel.openEditOrderDialog()
                },
                navBack = navBack,
                deleteOrder = {
                    viewModel.openDeleteOrderDialog()
                }
            )
        }, content = {
            OrderDetailsContent(
                order = order,
                id = id,
                navToCarpets = {
                    navToCarpets()
                }
            )
        }
    )
}



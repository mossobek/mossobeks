package com.stirkaparus.driver.presentation.order_details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.driver.presentation.order_details.components.OrderDetailsContent
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import es.dmoral.toasty.Toasty
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(
    id: String,
    navBack: () -> Unit,
    navToCarpets: () -> Unit,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        OrderDetailsTopBar()
    }, content = { paddingValues ->

        TryGetOrder(id = id, errorMessage = {
            Toasty.error(context, "Что то рошло не так", Toast.LENGTH_SHORT, true)
                .show()

        })
    })


}

@Composable
fun OrderDetailsTopBar() {


}


@Composable
fun TryGetOrder(
    errorMessage: (String) -> Unit, id: String, viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    viewModel.getOrder(id)
    when (val orderDetailsResponse = viewModel.orderDetailsResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> {
            OrderDetailsContent(
                order = orderDetailsResponse.data
            )
        }
        is Response.Failure -> {
            errorMessage(orderDetailsResponse.e?.message.toString())
        }
    }

}


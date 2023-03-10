package com.stirkaparus.driver.presentation.add_order

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.driver.presentation.add_order.components.AddOrderContent
import com.stirkaparus.driver.presentation.add_order.components.AddOrderToDB
import com.stirkaparus.driver.presentation.add_order.components.AddOrderTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddOrderScreen(
    navController: NavController,
    paddingValue: PaddingValues,
    viewModel: AddOrderViewModel = hiltViewModel()

) {
    Scaffold(
        topBar = {
            AddOrderTopBar()
        }, content = { padding ->
            AddOrderContent(
                padding = padding,
                addOrder = {order ->
                    viewModel.addOrderInFirestore(order)
                }
            )
        }
    )
    AddOrderToDB()

}

package com.stirkaparus.driver.presentation.addOrder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.driver.presentation.addOrder.components.AddOrderContent
import com.stirkaparus.driver.presentation.addOrder.components.AddOrderTopBar

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
                addOrder = { order ->
                    viewModel.addOrderInFirestore(order)
                }
            )
        }
    )

}

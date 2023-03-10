package com.stirkaparus.driver.presentation.add_order.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.add_order.AddOrderViewModel
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.model.Response

@Composable
fun AddOrderToDB(
    addOrderViewModel: AddOrderViewModel = hiltViewModel()
) {
    when (val addOrderResponse = addOrderViewModel.addOrderInFirestoreResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addOrderResponse.e)
        }
    }
}
package com.stirkaparus.driver.presentation.addOrder.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
 import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.addOrder.AddOrderViewModel
import com.stirkaparus.driver.presentation.components.ProgressDialog
import com.stirkaparus.model.Response

@Composable
fun AddOrderInFirestore(
    addOrderViewModel: AddOrderViewModel = hiltViewModel()
) {
     when (val addOrderInFirestoreResponse = addOrderViewModel.addOrderInFirestoreResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addOrderInFirestoreResponse.e)
        }
    }
}
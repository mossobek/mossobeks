package com.stirkaparus.stirkaparus.presentation.add_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response.*
import com.stirkaparus.stirkaparus.presentation.add_screen.AddOrderViewModel
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog

@Composable
fun AddOrderToDB(
    addOrderViewModel: AddOrderViewModel = hiltViewModel()
) {
    when(val addOrderResponse =  addOrderViewModel.addOrderInFirestoreResponse) {
        is Loading -> ProgressDialog()
        is Success -> Unit
        is Failure -> LaunchedEffect(Unit) {
            print(addOrderResponse.e)
        }
    }
}
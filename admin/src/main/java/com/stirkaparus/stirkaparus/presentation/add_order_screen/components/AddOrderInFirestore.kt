package com.stirkaparus.stirkaparus.presentation.add_order_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.add_order_screen.AddOrderViewModel
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog

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
package com.stirkaparus.stirkaparus.presentation.carpets.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel

@Composable
fun AddCarpet(
    viewModel: CarpetsViewModel = hiltViewModel()
) {
    when (val addCarpetResponse = viewModel.addCarpetResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> Unit
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addCarpetResponse.e)
        }
    }
}
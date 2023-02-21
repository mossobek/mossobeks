package com.stirkaparus.stirkaparus.presentation.add_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.add_screen.components.AddOrderContent
import com.stirkaparus.stirkaparus.presentation.add_screen.components.AddOrderToDB
import com.stirkaparus.stirkaparus.presentation.add_screen.components.AddOrderTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(
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
    AddOrderToDB()
}

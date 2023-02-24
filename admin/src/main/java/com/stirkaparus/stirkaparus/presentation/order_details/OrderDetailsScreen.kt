package com.stirkaparus.stirkaparus.presentation.order_details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.order_details.components.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(
    id: String,
    navController: NavController,
    navBack: () -> Unit,
    navToCarpets: () -> Unit,
    editOOrder: () -> Unit
) {


    Scaffold(topBar = {
        OrderDetailsTopBar(
            editOrder = editOOrder,
            navBack = navBack,
            deleteOrder = {
                //TODO
            }
        )
    }, content = {
        OrderDetailsContent(
            id = id,
            navToCarpets = {
                navToCarpets()
            }
        )
    }
    )
}




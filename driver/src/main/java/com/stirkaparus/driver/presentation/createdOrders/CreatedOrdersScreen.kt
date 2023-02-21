package com.stirkaparus.driver.presentation.createdOrders

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.presentation.orders.components.OrdersScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreatedOrders(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    OrdersScreen(
        navController = navController,
        paddingValue = paddingValues,
        filter = Constants.CREATED
    )

}

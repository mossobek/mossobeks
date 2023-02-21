package com.stirkaparus.driver.presentation.finishedOrders

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.presentation.orders.components.OrdersScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FinishedOrders(
    navController: NavHostController,
    paddingValue: PaddingValues,
) {
        OrdersScreen(
            navController = navController,
            paddingValue = paddingValue,
            filter = Constants.FINISHED
        )



}
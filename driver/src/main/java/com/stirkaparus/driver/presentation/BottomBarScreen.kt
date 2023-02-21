package com.stirkaparus.driver.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Orders : BottomBarScreen(
        route = "ORDERS",
        title = "ORDERS",
        icon = Icons.Default.Upload
    )

    object Add : BottomBarScreen(
        route = "ADD",
        title = "ADD",
        icon = Icons.Default.Add
    )
    object Reports : BottomBarScreen(
        route = "REPORTS",
        title = "REPORTS",
        icon = Icons.Default.Report

    )
}
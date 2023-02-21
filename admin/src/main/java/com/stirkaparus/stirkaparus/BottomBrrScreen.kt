package com.stirkaparus.stirkaparus

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Orders : BottomBarScreen(
        route = "ORDERS",
        title = "ORDERS",
        icon = Icons.Default.Reorder
    )


    object Add : BottomBarScreen(
        route = "ADD",
        title = "ADD",
        icon = Icons.Default.Add
    )

    object Reports : BottomBarScreen(
        route = "REPORT",
        title = "REPORT",
        icon = Icons.Default.Assessment
    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )

}
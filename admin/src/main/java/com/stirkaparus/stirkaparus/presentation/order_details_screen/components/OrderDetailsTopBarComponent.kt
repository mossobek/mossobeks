package com.stirkaparus.stirkaparus.presentation.order_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrderDetailsScreen1


@Composable
fun OrderDetailsTopBarComponent(navController: NavController, id: String, deleteOrder: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier

        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close icon button"
                )
            }
        }
        Row(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier, style = MaterialTheme.typography.h6, text = "Детали заказа"
            )
        }
        Row(
            modifier = Modifier
        ) {
            IconButton(modifier = Modifier.padding(end = 0.dp), onClick = {
                navController.navigate(OrderDetailsScreen1.Edit1.route + "/${id}")
            }) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "Edit icon button"
                )
            }
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {
                deleteOrder()
            }) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Delete icon button"
                )
            }
        }
    }
}





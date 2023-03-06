package com.stirkaparus.stirkaparus.presentation.orders_list_screen.components


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrdersViewModel

@Composable
fun OrdersTopBar(
    viewModel: OrdersViewModel = hiltViewModel()
) {
    TopAppBar(
        title = {
            Text(
                text = "Заказы",
                style = MaterialTheme.typography.h4
            )
        },
        actions = {
            IconButton(

                onClick = {

                    //search

                }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )

            }
            IconButton(onClick = {
                viewModel.sortSectionOpen()

            }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort"
                )

            }


        }
    )
}
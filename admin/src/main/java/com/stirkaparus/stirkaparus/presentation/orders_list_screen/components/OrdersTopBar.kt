package com.stirkaparus.stirkaparus.presentation.orders_list_screen.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.OrdersViewModel

@Composable
fun OrdersTopBar(
    viewModel: OrdersViewModel = hiltViewModel()
) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, color = Color.Black, text = "Заказы"
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
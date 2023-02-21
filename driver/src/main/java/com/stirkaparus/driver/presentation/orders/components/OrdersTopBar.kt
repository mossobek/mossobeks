package com.stirkaparus.driver.presentation.orders.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.orders.SearchViewModel


@Composable
fun OrdersTopBar(
    onSearchClicked: () -> Unit,
    text: String,
    sortClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),

    ) {

    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    Column {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {

                TopAppBar(
                    elevation = 4.dp,
                    title = {
                        Text(text)
                    },
                    backgroundColor = Color.White,
                    actions = {
                        IconButton(onClick = {
                            onSearchClicked()
                        }) {
                            Icon(Icons.Filled.Search, null)
                        }
                        IconButton(onClick = {
                            sortClick()
                        }) {
                            Icon(Icons.Filled.Sort, null)
                        }
                    })
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = onSearchClicked
                )

            }
        }
    }
}


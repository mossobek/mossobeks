package com.stirkaparus.driver.presentation.orders.components

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.presentation.orders.SearchViewModel
import com.stirkaparus.driver.ui.theme.BackgroundColor

@Composable
fun OrdersScreen(
    filter: String = Constants.CREATED,
    navController: NavHostController,
    paddingValue: PaddingValues,
    searchViewModel: SearchViewModel = hiltViewModel(),

    ) {
    val scaffoldState = rememberScaffoldState()
    Log.e(ContentValues.TAG, "$filter: ")
    Log.e(ContentValues.TAG, "${searchViewModel.searchTextState.value}: ")

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = paddingValue.calculateBottomPadding()),
        scaffoldState = scaffoldState,
        topBar = {
            OrdersTopBar(
                text = "Забрать",
                onSearchClicked = {
                    searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)

                },
                sortClick = {

                },
                onCloseClicked = {
                    searchViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onTextChange = {
                    searchViewModel.updateSearchTextState(newValue = it)

                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
                    .fillMaxWidth()
                    .padding(top = 6.dp)
                    .padding(start = 6.dp)
                    .padding(end = 6.dp)
            ) {
                Orders(ordersContent = { orders ->
                    Log.e(ContentValues.TAG, "$filter: orders->$orders")
                    OrdersContent(
                        status = filter,
                        searchText = searchViewModel.searchTextState as MutableState<String>,
                        padding = padding,
                        orders = orders.filter { it.status == filter },
                        navController = navController
                    )
                }

                )
            }

        })
}
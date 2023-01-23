package com.stirkaparus.stirkaparus.screens.orders_list_screen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.stirkaparus.stirkaparus.graphs.Graph
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.order_details_screen.OrderDetailsScreen
import com.stirkaparus.stirkaparus.screens.order_edit_screen.OrderEditScreen
import com.stirkaparus.stirkaparus.screens.orders_list_screen.components.OrderItem
import com.stirkaparus.stirkaparus.screens.orders_list_screen.components.SortSection
import com.stirkaparus.stirkaparus.ui.theme.BackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun OrdersScreen(
    navController: NavHostController
) {

    val filterList: List<String> = listOf("created", "taken")
    val viewModel: OrdersViewModel = OrdersViewModel()
    val postsResource by viewModel.fetchPosts().collectAsState(initial = Resource.loading(null))
    val posts = postsResource.data ?: emptyList()
    var filteredPosts by remember { mutableStateOf(posts) }
    val scaffoldState = rememberScaffoldState()
    var state by remember { mutableStateOf(false) }
    val selectedFilter = remember {
        mutableStateOf("")
    }



    Scaffold(
        modifier =
        Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(

                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row() {

                    Text(
                        text = "Заказы",
                        style = MaterialTheme.typography.h4
                    )
                }
                Row() {

                    IconButton(

                        onClick = {
                            //add new order
                        }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )

                    }
                    IconButton(onClick = {
                        state = !state
                        Log.e(TAG, "OrdersScreen: $state")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort"
                        )

                    }
                }
            }

            filteredPosts = filterOrder(selectedFilter, posts)
            AnimatedVisibility(
                visible = state,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    orderSort = selectedFilter.value,
                    onFilterChange =
                    selectedFilter

                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredPosts) { order ->
                    if (order != null) {
                        OrderItem(
                            order = order,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {


                                        navController.navigate(Graph.ORDER_DETAIL+"/${order.id}")

                                    }
                                ),
                            onItemMenuClick = {
                                // delete order

                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}

fun NavGraphBuilder.orderNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.ORDER_DETAIL+"/{id}",
        startDestination = OrderDetailsScreen1.OrderDetails.route+"/{id}"
    ) {
        composable(
            route = OrderDetailsScreen1.OrderDetails.route+"/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {

            OrderDetailsScreen(navController = navController)
        }
        composable(route = OrderDetailsScreen1.Edit1.route+"/{id}") {
            OrderEditScreen(navController = navController)
        }
    }
}


sealed class OrderDetailsScreen1(val route: String) {

    object OrderDetails : OrderDetailsScreen1(route = "ORDER_DETAIL")
    object Edit1 : OrderDetailsScreen1(route = "EDIT1")
}


@Composable
private fun filterOrder(
    selectedFilter: MutableState<String>,
    posts: List<Order?>
): List<Order?> {
    val filteredPosts = when (selectedFilter.value) {
        Filter.created -> {
            posts.filter { it?.status == "created" }
        }
        Filter.taken -> {
            posts.filter { it?.status == "taken" }
        }
        Filter.washed -> {
            posts.filter { it?.status == "washed" }
        }
        Filter.finished -> {
            posts.filter { it?.status == "finished" }
        }
        Filter.delivered -> {
            posts.filter { it?.status == "delivered" }
        }
        Filter.all -> {
            posts
        }
        else -> {
            posts
        }
    }
    return filteredPosts
}

object Filter {
    const val all = "all"
    const val created = "created"
    const val taken = "taken"
    const val washed = "washed"
    const val finished = "finished"
    const val delivered = "delivered"
}

@Composable
private fun GetPosts(
    postsResource: Resource<List<Order?>>,
    posts: List<Order?>
) {
    if (postsResource.status == Status.ERROR) {
        Text("Error: ${postsResource.message}")
    } else if (postsResource.status == Status.LOADING) {
        Text("Loading ....")
    } else {

        Log.e(ContentValues.TAG, "OrdersScreen: $posts")


    }
}





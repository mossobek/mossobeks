package com.stirkaparus.stirkaparus.presentation.orders_list_screen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stirkaparus.stirkaparus.graphs.Graph
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.showToast
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.OrderItem
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.SortSection
import com.stirkaparus.stirkaparus.ui.theme.BackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun OrdersScreen(
    navController: NavHostController,
    paddingValue: PaddingValues,
    viewModel: OrdersViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val filterList: List<String> = listOf("created", "taken")
     val postsResource by viewModel.fetchPosts().collectAsState(initial = Resource.loading(null))
    val posts = postsResource.data ?: emptyList()
    var filteredPosts by remember { mutableStateOf(posts) }
    val scaffoldState = rememberScaffoldState()
    var state by remember { mutableStateOf(false) }
    val selectedFilter = remember {
        mutableStateOf("")
    }
    var loading by remember { mutableStateOf(false) }
    if (loading)  ProgressDialog()


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValue.calculateBottomPadding()),
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxWidth()
                .padding(top = 6.dp)
                .padding(start = 6.dp)
                .padding(end = 6.dp)
        ) {
            Row(

                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(3f),
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        text = "Заказы",
                        style = MaterialTheme.typography.h4
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {

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
            Spacer(modifier = Modifier.height(6.dp))



            if (posts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .background(BackgroundColor)
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .padding(start = 6.dp)
                        .padding(end = 6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(fontSize = 25.sp, text = "Список пуст")
                }
            }



             LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(filteredPosts) { order ->
                    if (order != null) {
                        OrderItem(
                            order = order,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    navController.navigate(Graph.ORDER_DETAIL + "/${order.id}")
                                }),
                            onDeleteItemMenuClick = {

                                loading = true
//                                viewModelEditText.deleteOrder(
//                                    order.id,
//                                    success = {
//                                        showToast(context, "Заказ удален!")
//                                        loading = false
//                                    },
//                                    failure = {
//                                        showToast(context, "Что то пошло не так...")
//                                        loading = false
//
//                                    })


                            },
                            onPhoneClick = {
                                val callPhone = order.phone
                                val callIntent = Intent(Intent.ACTION_DIAL)
                                callIntent.data = Uri.parse("tel:$callPhone")
                                startActivity(context, callIntent, null)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

    }
}


sealed class OrderDetailsScreen1(val route: String) {

    object OrderDetails : OrderDetailsScreen1(route = "ORDER_DETAIL")
    object Edit1 : OrderDetailsScreen1(route = "EDIT1")
    object Carpets : OrderDetailsScreen1(route = "CARPETS")
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
            posts.filter { it?.status != "deleted" }
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





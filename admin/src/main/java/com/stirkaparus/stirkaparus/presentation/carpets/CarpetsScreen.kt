package com.stirkaparus.stirkaparus.presentation.carpets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetAlertDialog
import com.stirkaparus.stirkaparus.presentation.carpets.components.CarpetItem
import com.stirkaparus.stirkaparus.presentation.order_details_screen.Resource
import com.stirkaparus.stirkaparus.ui.theme.BackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CarpetsScreen(navController: NavController) {
    
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    var carpetAlertDialogState by remember { mutableStateOf(false) }
    val viewModel = CarpetsViewModel()
    val context = LocalContext.current
    val carpetsResource by viewModel.fetchCarpets(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val carpets = carpetsResource.data ?: emptyList()
    var loading by remember { mutableStateOf(false) }





    if (carpetAlertDialogState) AddCarpetAlertDialog(id = id.toString(),
        viewModel = viewModel,
        context = context,
        onClick = {},
        onDismiss = { carpetAlertDialogState = false })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {


        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading)  ProgressDialog()

            TopAppBar(
                modifier = Modifier.fillMaxWidth(),

                ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Add")
                    }
                    Text(
                        color = Color.Black, fontSize = 28.sp, text = "Ковры заказа"
                    )
                    IconButton(enabled = false, onClick = {
                        carpetAlertDialogState = true
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }

            }
            if (carpets.isEmpty()) {
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
                items(carpets) { carpet ->
                    if (carpet != null) {
                        CarpetItem(carpet = carpet, onDeleteItemMenuClick = {
                            loading = true
                            viewModel.deleteCarpet(orderId = id, carpetId = carpet.id, success = {
                                loading = false
                            }, failure = {
                                loading = false

                            })
                        }) {

                        }
                    }

                }
            }

        }
    }

}


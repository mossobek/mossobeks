package com.stirkaparus.stirkaparus.presentation.carpets

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.exponentialDecay
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.TAG
import com.stirkaparus.stirkaparus.presentation.carpets.components.AddCarpetSheet
import com.stirkaparus.stirkaparus.presentation.carpets.components.CarpetItem
import com.stirkaparus.stirkaparus.presentation.carpets.components.EditCarpetDialog
import com.stirkaparus.stirkaparus.presentation.order_details.Resource
import com.stirkaparus.stirkaparus.ui.theme.BackgroundColor
import com.stirkaparus.stirkaparus.ui.theme.Blue17

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CarpetsScreen(
    navController: NavController,
    viewModel: CarpetsViewModel = hiltViewModel(),
) {

    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    val carpetsResource by viewModel.fetchCarpets(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val carpets = carpetsResource.data ?: emptyList()

    EditCarpetDialog(
        openDialog = viewModel.editCarpetDialog
    )



    Scaffold(
        topBar = {
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
                        //                  carpetAlertDialogState = true
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Blue17),
        content = {


            Column(
                modifier = Modifier
                    .background(BackgroundColor)
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                if (carpets.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .background(BackgroundColor)
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(fontSize = 25.sp, text = "Список пуст")
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(carpets) { carpet ->
                        if (carpet != null) {
                            CarpetItem(
                                carpet = carpet,
                                onDeleteClick = {

                                    viewModel.openDeleteCarpetDialog(it)

                                },
                                onEditClick = {

                                    viewModel.openEditCarpetDialog(it)

                                }
                            )

                        }
                        Spacer(modifier = Modifier.height(11.dp))

                    }
                }

            }
        }
    )
}


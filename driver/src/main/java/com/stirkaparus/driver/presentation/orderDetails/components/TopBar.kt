package com.stirkaparus.driver.presentation.orderDetails.components

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.stirkaparus.driver.presentation.orderDetails.StatusDetails


@Composable
fun TopBar(
    navController: NavController,
    cancelAlert: MutableState<Boolean>,
    text: String,
    statusDetails: StatusDetails
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        title = { Text(text = text) },
        actions = {
            if (statusDetails == StatusDetails.Created) {
                Log.e(ContentValues.TAG, "TopBar: $statusDetails", )
                IconButton(
                    onClick = {
                        cancelAlert.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Add",
                        tint = Color.Black
                    )
                }
            }

        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Nav back",
                    tint = Color.Black
                )
            }
        }
    )
}

